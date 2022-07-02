package com.hgws.sbp.commons.annotation;

import com.hgws.sbp.commons.enumerate.TypeEnumerate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-30 17:27
 * @description: TODO
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoggerOperation {
    /**
     * 被操作的模块: 模块名称
     */
    String module() default "";
    /**
     * 方法描述: 可使用占位符获取参数:{{name}}
     */
    String message() default "";
    /**
     * 操作类型(enum): insert,delete,update,select
     */
    TypeEnumerate type() default TypeEnumerate.SELECT;
}
