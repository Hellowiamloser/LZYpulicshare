package com.hgws.sbp.commons.annotation;

import java.lang.annotation.*;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-10-27
 * @description: 数据字典封装
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DictionaryOperation {
    /**
     * 字典分类
     */
    String code() default "";
}
