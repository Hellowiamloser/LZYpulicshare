package com.hgws.sbp.configurations.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-11 16:24
 * @description: swagger3在线文档
 */
@EnableOpenApi
@Configuration
public class SwaggerConfiguration {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("\u5728\u7ebf\u63a5\u53e3\u6587\u6863")
                .description("\u8be5\u63a5\u53e3\u6587\u6863\u65e8\u5728\u63d0\u4f9b\u524d\u7aef\u5de5\u7a0b\u5e08\u8c03\u7528\u540e\u53f0\u63a5\u53e3\u63d0\u4f9b\u8bf4\u660e\u89c4\u8303")
                .contact(new Contact("\u5468\u5b8f\u521a", "http://127.0.0.1:9527/swagger-ui/", "honggang_z@163.com"))
                .version("1.2.0")
                .build();
    }

}