package com.hgws.sbp.configurations.security.encoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-05 16:59
 * @description: 配置密码解析器
 */
@Configuration
public class PasswordEncoderConfig {
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
}
