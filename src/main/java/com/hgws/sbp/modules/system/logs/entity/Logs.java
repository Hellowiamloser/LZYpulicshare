package com.hgws.sbp.modules.system.logs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hgws.sbp.commons.base.entity.Base;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-01 15:55
 * @description: TODO
 */
@Getter
@Setter
@TableName("system_logger")
public class Logs extends Base {
    /** 操作模块 */
    private String module;
    /** 操作方法 */
    private String type;
    /** 操作日志 */
    private String message;
    /** 请求路径 */
    private String requestUrl;
    /** 请求方法 */
    private String requestMethod;
    /** 请求IP地址 */
    private String requestIpaddr;
    /** 耗时时间 */
    private Long executeTime;
    /** 输入参数 */
    private String executeParams;
    /** 返回结果 */
    private String returnValue;
}
