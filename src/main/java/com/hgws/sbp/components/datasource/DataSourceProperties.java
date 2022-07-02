package com.hgws.sbp.components.datasource;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-01 17:21
 * @description: 数据源读取配置信息
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}