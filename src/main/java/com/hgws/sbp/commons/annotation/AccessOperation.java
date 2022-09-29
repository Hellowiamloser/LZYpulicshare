package com.hgws.sbp.commons.annotation;

import java.lang.annotation.*;
/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-09-29
 * @description: 匿名访问
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessOperation {
}
