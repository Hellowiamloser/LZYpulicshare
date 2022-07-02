package com.hgws.sbp.commons.base.result;

import com.hgws.sbp.commons.enumerate.ResultEnumerate;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-02 11:09
 * @description: 统一返回封装
 */
@Getter
@Setter
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result() {}

    public Result(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功 | 无参
     * @return Result
     */
    public static Result success() {
        Result result = new Result();
        result.setResultEnumerate(ResultEnumerate.INTERFACE_CALL_SUCCESS);
        return result;
    }

    /**
     * 成功 | 带参
     * @return Result
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setResultEnumerate(ResultEnumerate.INTERFACE_CALL_SUCCESS);
        result.setData(data);
        return result;
    }

    /**
     * 自定义成功 | 带参
     * @return Result
     */
    public static Result success(ResultEnumerate resultCode) {
        Result result = new Result();
        result.setResultEnumerate(resultCode);
        return result;
    }

    /**
     * 自定义成功 | 带参
     * @return Result
     */
    public static Result success(ResultEnumerate resultCode, Object data) {
        Result result = new Result();
        result.setResultEnumerate(resultCode);
        result.setData(data);
        return result;
    }

    /**
     * 错误信息 | 无参
     * @return Result
     */
    public static Result failure() {
        Result result = new Result();
        result.setResultEnumerate(ResultEnumerate.INTERFACE_INNER_ERROR);
        return result;
    }

    /**
     * 错误信息 | 无参
     * @return Result
     */
    public static Result failure(ResultEnumerate resultCode) {
        Result result = new Result();
        result.setResultEnumerate(resultCode);
        return result;
    }

    /**
     * 错误信息 | 带参
     * @return Result
     */
    public static Result failure(ResultEnumerate resultCode, Object data) {
        Result result = new Result();
        result.setResultEnumerate(resultCode);
        result.setData(data);
        return result;
    }

    public void setResultEnumerate(ResultEnumerate code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
