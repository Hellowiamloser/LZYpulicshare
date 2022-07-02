package com.hgws.sbp.commons.base.controller;

import com.hgws.sbp.commons.utils.ServletUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project springboot-development-platform
 * @datetime 2022-07-02 15:10
 * @description: 公共表现层实例
 */
public class BaseController {
    /**
     * 获取request
     */
    public HttpServletRequest getRequest()
    {
        return ServletUtils.getRequest();
    }

    /**
     * 获取response
     */
    public HttpServletResponse getResponse()
    {
        return ServletUtils.getResponse();
    }
}
