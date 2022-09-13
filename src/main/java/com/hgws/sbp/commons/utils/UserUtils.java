package com.hgws.sbp.commons.utils;

import com.hgws.sbp.components.properties.SpringSecurityProperties;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-06 09:24
 * @description: 获取登陆用户数据
 */
@Component
public class UserUtils {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private SpringSecurityProperties springSecurityProperties;

    private final static String ANONYMITY_TOKEN = "eyJhbGciOiJIUzUxMiIsInppcCI6IkRFRiJ9.eNqqVspMUbIy0FEqLU4tykvMTVWyUnras__phF4lHaXM4mIgN6OkpMBKX9_QyFzPAAgN9YEyxaVJKAoTS5SsDM1MzQ3NTU2MjHSUUisKYAKWBkZGtQAAAAD__w.vusoAyOqjCIK7V6YNm52ruzOBEEFkIdjNg2ASqaHB-6yTjY7eR7mJPVmmRFg_ogYv2ka4vTptfh-UXKRKGcr_g";

    /**
     * 获取当前登陆用户主键ID
     * @return 用户主键ID
     */
    public int getUserId()
    {
        Claims claims = jwtUtils.getTokenClaims(getToken());
        return (int)claims.get("id");
    }

    /**
     * 获取当前登陆用户账号
     * @return 用户登陆账号
     */
    public String getUserName()
    {
        Claims claims = jwtUtils.getTokenClaims(getToken());
        return (String)claims.get("username");
    }

    /**
     * 获取操作人token
     * @return token
     */
    public String getToken()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader(springSecurityProperties.getJwt().getHeader());
        String prefix = springSecurityProperties.getJwt().getPrefix();
        if(StringUtils.hasLength(token) && token.startsWith(prefix)) {
            return token.substring(prefix.length());
        }
        return ANONYMITY_TOKEN;
    }

}
