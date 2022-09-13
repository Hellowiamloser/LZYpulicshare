package com.hgws.sbp.commons.utils;

import com.hgws.sbp.components.properties.SpringSecurityProperties;
import io.jsonwebtoken.*;
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
 * @description: jwt工具类
 */
@Getter
@Setter
@Component
public class JwtUtils {

    @Autowired
    private SpringSecurityProperties properties;

    /**
     * 生成认证访问token
     * @param username 账号
     * @return 认证token
     */
    public String accessToken(int id, String username) {
        return createTokenTimes(id, username, properties.getJwt().getExpiration());
    }

    /**
     * 生成认证刷新token
     * @param username 账号
     * @return 认证token
     */
    public String refreshToken(int id, String username) {
        return createTokenTimes(id, username, properties.getJwt().getRefresh());
    }

    /**
     * 生成认证token
     * @param username 账号
     * @return 认证token
     */
    public String createTokenTimes(int id, String username, long times) {
        return Jwts.builder()
            .claim("id", id)
            //用户基础信息
            .claim("username", username)
            //签发标识
            .setIssuer(properties.getJwt().getIssuer())
            //签发账号
            .setSubject(username)
            //设置签名秘钥
            .signWith(SignatureAlgorithm.HS512, properties.getJwt().getSecret())
            //设置签发时间
            .setIssuedAt(new Date())
            //设置签发过期时间
            .setExpiration(new Date(System.currentTimeMillis() + times * 1000))
            //对于荷载内容压缩
            .compressWith(CompressionCodecs.DEFLATE)
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
