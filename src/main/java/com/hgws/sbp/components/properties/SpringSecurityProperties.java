package com.hgws.sbp.components.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-05 13:56
 * @description: SpringSecurity配置信息
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "security")
public class SpringSecurityProperties {
    /**
     * SpringSecurity白名单
     */
    private String[] whiteList;
    /**
     * SpringSecurity jwt token属性
     */
    private SpringSecurityProperties.Jwt jwt;

    @Data
    public static class Jwt {
        /**
         * 请求header名称
         */
        private String header;
        /**
         * 请求header前缀
         */
        private String prefix;
        /**
         * token加密key
         */
        private String secret;
        /**
         * token签发域名
         */
        private String issuer;
        /**
         * token过期时间
         */
        private long expiration;
        /**
         * token刷新时长
         */
        private long refresh;
        /**
         * token记住我时间
         */
        private long remember;
    }
}