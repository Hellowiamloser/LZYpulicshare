package com.hgws.sbp.commons.validator.password;

import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-04-13 11:37
 * @description: 自定义校验规则实现
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {
    /**
     * 数字规则
     */
    public static final String NUMBER = ".*\\d+.*";
    /**
     * 大写字母规则
     */
    public static final String UPPERCASE = ".*[A-Z]+.*";
    /**
     * 小写字母规则
     */
    public static final String LOWERCASE = ".*[a-z]+.*";
    /**
     * 特殊符号规则
     */
    public static final String SYMBOL = ".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(StringUtils.hasText(value))
        {
            int count = 0;
            if (value.matches(PasswordValidator.NUMBER)) {
                count++;
            }
            if (value.matches(PasswordValidator.LOWERCASE)) {
                count++;
            }
            if (value.matches(PasswordValidator.UPPERCASE)) {
                count++;
            }
            if (value.matches(PasswordValidator.SYMBOL)) {
                count++;
            }
            return count > 2;
        }
        return false;
    }
}
