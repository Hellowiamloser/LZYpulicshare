package com.hgws.sbp.commons.enumerate;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-01 17:33
 * @description: 统一返回枚举封装
 */
public enum ResultEnumerate {
    /* 登陆相关：1001-1999 */
    LOGIN_SUCCESS(1001, "登陆成功"),
    LOGOUT_SUCCESS(1002, "退出成功"),
    LOGIN_USER_NOT_EXIST(1003, "账号输入错误"),
    LOGIN_PASS_INPUT_ERROR(1004, "密码输入错误"),
    LOGIN_USER_PASS_ERROR(1005, "账号或密码输入错误"),
    LOGIN_USER_LOCKED(1006, "账号已被锁定"),
    LOGIN_USER_DISABLED(1007, "账号已被禁用"),
    LOGIN_OTHER_ERROR(1008, "其他登陆失败"),
    LOGIN_NOT_LOGGED(1009, "用户未登录"),
    UNAUTHORIZED_ACCESS(1010, "用户未授权"),
    TOKEN_ALREADY_EXPIRED(1011, "token已过期"),

    /* 接口相关：2001-2999 */
    INTERFACE_CALL_SUCCESS(2001, "接口调用成功"),
    INTERFACE_CALL_ERROR(2002, "接口调用异常"),
    INTERFACE_INNER_ERROR(2003, "内部接口调用异常"),
    INTERFACE_OUTER_ERROR(2004, "外部接口调用异常"),
    INTERFACE_FORBID_VISIT(2005, "该接口禁止访问"),
    INTERFACE_REQUEST_TIMEOUT(2006, "接口请求超时"),
    INTERFACE_EXCEED_LOAD(2007, "接口负载过高"),
    INTERFACE_NOT_FOUND(2008, "接口不存在"),

    /* 参数错误：3001-3999 */
    PARAM_IS_BLANK(3001, "参数为空"),
    PARAM_IS_INVALID(3002, "参数无效"),
    PARAM_TYPE_ERROR(3003, "参数类型异常"),
    PARAM_NOT_COMPLETE(3004, "参数缺失异常");

    //响应状态码
    private Integer code;
    //响应文本值
    private String message;

    ResultEnumerate(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
