package com.hgws.sbp.configurations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.constant.Constant;
import com.hgws.sbp.commons.enumerate.ResultEnumerate;
import com.hgws.sbp.commons.enumerate.TypeEnumerate;
import com.hgws.sbp.commons.utils.JwtUtils;
import com.hgws.sbp.components.properties.SpringSecurityProperties;
import com.hgws.sbp.components.redis.RedisComponent;
import com.hgws.sbp.components.result.ResponseResult;
import com.hgws.sbp.modules.system.logs.service.LogsService;
import com.hgws.sbp.modules.system.user.entity.SystemUser;
import com.hgws.sbp.modules.system.user.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-02 16:24
 * @description: SpringSecurity
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfiguration {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private LogsService logsService;

    @Autowired
    private SystemUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisComponent redisComponent;

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    private SpringSecurityProperties springSecurityProperties;

    /**
     * UserDetailsService身份认证
     * 实现AuthenticationProvider的DaoAuthenticationProvider完成身份认证
     * @return UserDetailsService
     */
    public UserDetailsService userDetailsService()
    {
        return username -> {
            SystemUser user = userService.loadUserByUsername(username);
            boolean accountNonLocked = true;
            if(ObjectUtils.isEmpty(user)) {
                throw new UsernameNotFoundException(Constant.USER_NOT_FOUND);
            } else if(user.getLocked() == 1) {
                accountNonLocked = false;
            }
            //throw new LockedException("用户已锁定");
            List<GrantedAuthority> authorities = new ArrayList<>();
            return new org.springframework.security.core.userdetails.User(user.getName(), user.getPass(), true, true, true, accountNonLocked, authorities);
        };
    }

    /**
     * 认证前置检查
     * @return UserDetailsChecker
     */
    public UserDetailsChecker userDetailsChecker()
    {
        return details -> {
            if(!details.isAccountNonLocked()) {
                throw new LockedException(Constant.USER_WAS_LOCKED);
            }
        };
    }

    /**
     * DaoAuthenticationProvider身份认证
     * @return DaoAuthenticationProvider
     * @throws Exception Exception
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //显示用户不存在异常
        provider.setHideUserNotFoundExceptions(false);
        //注入密码解析器
        provider.setPasswordEncoder(passwordEncoder);
        //注入查找用户
        provider.setUserDetailsService(userDetailsService());
        //完成前置校验
        provider.setPreAuthenticationChecks(userDetailsChecker());
        provider.afterPropertiesSet();
        return provider;
    }

    /**
     * 配置SpringSecurity安全过滤器链
     * @param http  HttpSecurity
     * @param configuration AuthenticationConfiguration
     * @return SecurityFilterChain
     * @throws Exception Exception
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationConfiguration configuration) throws Exception {
        return http
            // 前后端分离应用关闭csrf
            .csrf().disable()
            // 前后端分离开启跨域访问
            .cors()
                .and()
            // 会话管理策略: 禁用会话
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            // 配置客户端请求拦截规则
            .authorizeRequests()
                // 白名单放行
                .antMatchers(springSecurityProperties.getWhiteList()).permitAll()
                // 其他请求需经过认证后放行
                .anyRequest().authenticated()
                .and()
            // 处理客户端401 403响应返回
            .exceptionHandling()
                .authenticationEntryPoint((request, response, exception) -> {
                    // 用户未登录
                    ResultEnumerate enumerate = ResultEnumerate.LOGIN_NOT_LOGGED;
                    // 返回响应到客户端
                    ResponseResult.result(Result.failure(enumerate), HttpStatus.FORBIDDEN.value());
                    // 日志记录
                    logsService.insert(0, Constant.UNLOGGED_ACCESS, TypeEnumerate.SELECT.getValue(), enumerate.getMessage(), null, null);
                })
                .accessDeniedHandler((request, response, exception) -> {
                    // 用户未授权
                    ResultEnumerate enumerate = ResultEnumerate.UNAUTHORIZED_ACCESS;
                    // 返回响应到客户端
                    ResponseResult.result(Result.failure(enumerate), HttpStatus.UNAUTHORIZED.value());
                    // 日志记录
                    logsService.insert(0, Constant.UNAUTHORIZED_ACCESS, TypeEnumerate.SELECT.getValue(), enumerate.getMessage(), null, null);
                })
                .and()
            // 登陆认证实现
            .authenticationProvider(daoAuthenticationProvider())
            /*
             * FilterChain 过滤器链 []
             * UsernamePasswordAuthenticationFilter是AbstractAuthenticationProcessingFilter针对使⽤⽤户名和密码进⾏⾝份验证⽽定制化的⼀个过滤器。
             * 默认的登录请求pattern为"/login"，并且为POST请求。这个过滤器就会委托认证管理器authenticationManager来验证登录。
             */
            .addFilter(new UsernamePasswordAuthenticationFilter(configuration.getAuthenticationManager()){
                /**
                 * 前后端分离参数收集
                 * @param request   请求
                 * @param response  响应
                 * @return  Authentication
                 * @throws AuthenticationException AuthenticationException
                 */
                @Override
                public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
                    // 登陆仅支持POST请求方式
                    if (request.getMethod().equals("POST")) {
                        try {
                            // 获取请求中 Content-Type: application/json 参数
                            InputStream inputStream = request.getInputStream();
                            var params = objectMapper.readValue(inputStream, Map.class);
                            // 创建SpringSecurity认证对象
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(params.get("username"), params.get("password"));
                            return this.getAuthenticationManager().authenticate(authenticationToken);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return super.attemptAuthentication(request, response);
                }
                /**
                 * 认证异常
                 * @param request   请求
                 * @param response  响应
                 * @param exception 异常
                 */
                @Override
                protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {
                    // 清空认证对象
                    SecurityContextHolder.clearContext();
                    // 默认其他登陆失败
                    ResultEnumerate enumerate = ResultEnumerate.LOGIN_OTHER_ERROR;
                    if(exception instanceof UsernameNotFoundException) {
                        //enumerate = ResultEnumerate.LOGIN_USER_NOT_EXIST;
                        enumerate = ResultEnumerate.LOGIN_USER_PASS_ERROR;
                    } else if(exception instanceof BadCredentialsException) {
                        //enumerate = ResultEnumerate.LOGIN_PASS_INPUT_ERROR;
                        enumerate = ResultEnumerate.LOGIN_USER_PASS_ERROR;
                    } else if(exception instanceof LockedException) {
                        enumerate = ResultEnumerate.LOGIN_USER_LOCKED;
                    }
                    // 返回响应到客户端
                    ResponseResult.result(Result.failure(enumerate));
                }
                /**
                 * 认证成功
                 * @param request   请求
                 * @param response  响应
                 * @param chain 过滤器链
                 * @param authentication 认证对象
                 */
                @Override
                protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
                    // 获取认证对象
                    org.springframework.security.core.userdetails.User user =
                            (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
                    // 获取登陆账号
                    String username = user.getUsername();
                    // 根据账号查询用户信息
                    SystemUser entity = userService.loadUserByUsername(username);

                    // 根据账号查询用户权限
                    List<String> authorities = userService.loadUserAuthorities(username);
                    redisComponent.set(Constant.AUTHORITIES_KEY+username, authorities);

                    String accessToken = jwtUtils.accessToken(entity.getId(), entity.getName());
                    String refreshToken = jwtUtils.refreshToken(entity.getId(), entity.getName());
                    // 返回响应到客户端
                    ResultEnumerate enumerate = ResultEnumerate.LOGIN_SUCCESS;
                    ResponseResult.result(Result.success(enumerate, Map.of(
                            "access_token", accessToken,
                            "refresh_token", refreshToken)));
                }
            })
            /*
             * 核心过滤器 认证拦截
             */
            .addFilter(new BasicAuthenticationFilter(configuration.getAuthenticationManager()) {
                // 拦截除登陆及白名单外所有请求
                @Override
                protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                    // 从请求头中获取token
                    String token = request.getHeader(springSecurityProperties.getJwt().getHeader());
                    // 获取token的前缀名称
                    String prefix = springSecurityProperties.getJwt().getPrefix();
                    if(StringUtils.hasLength(token) && token.startsWith(prefix)) {
                        // 获取真实的token
                        String realToken = token.substring(prefix.length());
                        // 判断token是否已经过期
                        if(jwtUtils.isExpiration(realToken)) {
                            ResultEnumerate enumerate = ResultEnumerate.TOKEN_ALREADY_EXPIRED;
                            ResponseResult.result(Result.success(enumerate), HttpStatus.FORBIDDEN.value());
                            return;
                        } else {
                            String username = jwtUtils.getUsername(realToken);
                            // 准备当前用户权限集合
                            Collection<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

                            List<String> authorities = (List<String>)redisComponent.get(Constant.AUTHORITIES_KEY+username);
                            authorities.forEach(code -> {
                                authoritiesList.add(new SimpleGrantedAuthority(code));
                            });

                            // 创建认证用户token对象
                            UsernamePasswordAuthenticationToken authenticationToken =
                                    new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
                            // 通知SpringSecurity已认证
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                    super.doFilterInternal(request, response, chain);
                }
            })
            // 退出登录逻辑处理
            .logout()
                .logoutSuccessHandler((request, response, authentication) -> {
                    // 可以完善加入token到黑名单至过期时间
                    // 清理security上下文对象
                    SecurityContextHolder.clearContext();
                    // 返回响应到客户端
                    ResultEnumerate enumerate = ResultEnumerate.LOGOUT_SUCCESS;
                    ResponseResult.result(Result.success(enumerate));
                })
            .and()
            .build();
    }

    /**
     * 实例化SpringSecurity认证管理器
     * @param configuration 认证配置
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
