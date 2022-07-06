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

    public int getUserId()
    {
        Claims claims = jwtUtils.getTokenClaims(getToken());
        return (int)claims.get("id");
    }

    public String getUserName()
    {
        Claims claims = jwtUtils.getTokenClaims(getToken());
        return (String)claims.get("username");
    }

    public String getToken()
    {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader(springSecurityProperties.getJwt().getHeader());
        String prefix = springSecurityProperties.getJwt().getPrefix();
        if(StringUtils.hasLength(token) && token.startsWith(prefix))
            return token.substring(prefix.length());
        return null;
    }

}
