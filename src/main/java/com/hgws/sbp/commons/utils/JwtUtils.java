package com.hgws.sbp.commons.utils;

import com.hgws.sbp.components.properties.SpringSecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-05 11:20
 * @description: TODO
 */
@Getter
@Setter
@Component
public class JwtUtils {

    @Autowired
    private SpringSecurityProperties properties;

    /**
     * 生成认证token
     * @param username 账号
     * @return 认证token
     */
    public String createToken(String username) {
        Claims claims = Jwts.claims();
        return Jwts.builder()
            //签发标识
            .setIssuer(properties.getJwt().getIssuer())
            //权限集合
            .setClaims(claims)
            //签发账号
            .setSubject(username)
            //设置签发时间
            .setIssuedAt(new Date())
            //设置签名秘钥
            .signWith(SignatureAlgorithm.HS512, properties.getJwt().getSecret())
            //设置签发过期时间
            .setExpiration(new Date(System.currentTimeMillis() + properties.getJwt().getExpiration() * 1000))
            .compact();
    }

    /**
     * 根据token获取账号信息
     * @param token 请求token
     * @return 账号
     */
    public String getUsername(String token){
        return getTokenClaims(token).getSubject();
    }

    /**
     * 根据token验证是否过期
     * @param token 请求token
     * @return 是否过期
     */
    public boolean isExpiration(String token) {
        return getTokenClaims(token).getExpiration().before(new Date());
    }

    /**
     * 根据token获取签署认证信息
     * @param token 请求token
     * @return 签署认证信息
     */
    public Claims getTokenClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(properties.getJwt().getSecret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            claims = e.getClaims();
        }
        return claims;
    }
}
