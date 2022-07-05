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
    private String[] whiteList;
    private SpringSecurityProperties.Jwt jwt;

    @Data
    public static class Jwt {
        private String header;
        private String prefix;
        private String secret;
        private String issuer;
        private long expiration;
        private long remember;
    }
}