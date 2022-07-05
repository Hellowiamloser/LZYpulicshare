package com.hgws.sbp.configurations.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.enumerate.ResultEnumerate;
import com.hgws.sbp.commons.utils.JwtUtils;
import com.hgws.sbp.components.properties.SpringSecurityProperties;
import com.hgws.sbp.components.result.ResponseResult;
import com.hgws.sbp.modules.system.user.entity.User;
import com.hgws.sbp.modules.system.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import java.util.*;

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
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SpringSecurityProperties springSecurityProperties;

    private final static String USER_NOT_FOUND = "账号不存在";
    private final static String USER_WAS_LOCKED = "账号已锁定";

    /**
     * 密码解析器: 单向密码解析器
     * 通过salt加盐保证每次生成密码不一样
     * @return BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    /**
     * UserDetailsService身份认证
     * 实现AuthenticationProvider的DaoAuthenticationProvider完成身份认证
     * @return UserDetailsService
     */
    public UserDetailsService userDetailsService()
    {
        return username -> {
            User user = userService.loadUserByUsername(username);
            boolean accountNonLocked = true;
            if(ObjectUtils.isEmpty(user))
                throw new UsernameNotFoundException(USER_NOT_FOUND);
            else if(user.getLocked() == 1)
                accountNonLocked = false;
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
            if(!details.isAccountNonLocked())
                throw new LockedException(USER_WAS_LOCKED);
        };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //显示用户不存在异常
        provider.setHideUserNotFoundExceptions(false);
        //注入密码解析器
        provider.setPasswordEncoder(passwordEncoder());
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
                    ResponseResult.result(Result.failure(enumerate));
                })
                .accessDeniedHandler((request, response, exception) -> {
                    // 用户未授权
                    ResultEnumerate enumerate = ResultEnumerate.UNAUTHORIZED_ACCESS;
                    // 返回响应到客户端
                    ResponseResult.result(Result.failure(enumerate));
                })
                .and()
            // 登陆认证实现
            .authenticationProvider(daoAuthenticationProvider())
            /*
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
                            Map<String, String> params = objectMapper.readValue(inputStream, Map.class);
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
                    // 默认其他登陆失败
                    ResultEnumerate enumerate = ResultEnumerate.LOGIN_OTHER_ERROR;
                    if(exception instanceof UsernameNotFoundException)
                        enumerate = ResultEnumerate.LOGIN_USER_NOT_EXIST;
                    else if(exception instanceof BadCredentialsException)
                        enumerate = ResultEnumerate.LOGIN_PASS_INPUT_ERROR;
                    else if(exception instanceof LockedException)
                        enumerate = ResultEnumerate.LOGIN_USER_LOCKED;
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
                    // 生成jwt token
                    String token = jwtUtils.createToken(username);
                    ResultEnumerate enumerate = ResultEnumerate.LOGIN_SUCCESS;
                    // 返回响应到客户端
                    ResponseResult.result(Result.success(enumerate, token));
                }
            })
            /*
             * 核心过滤器 认证拦截
             */
            .addFilter(new BasicAuthenticationFilter(configuration.getAuthenticationManager()) {
                @Override
                protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                    String token = request.getHeader(springSecurityProperties.getJwt().getHeader());
                    String prefix = springSecurityProperties.getJwt().getPrefix();
                    if(StringUtils.hasLength(token) && token.startsWith(prefix))
                    {
                        String realToken = token.substring(prefix.length());
                        if(!jwtUtils.isExpiration(realToken))
                        {
                            String username = jwtUtils.getUsername(realToken);
                            Collection<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
                            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authoritiesList);
                            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        }
                    }
                    super.doFilterInternal(request, response, chain);
                }
            })
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
