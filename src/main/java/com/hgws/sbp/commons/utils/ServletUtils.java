package com.hgws.sbp.commons.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-02 15:05
 * @description: TODO
 */
public class ServletUtils {

    /**
     * 获取ServletRequestAttributes
     * @return ServletRequestAttributes
     */
    public static ServletRequestAttributes getRequestAttributes()
    {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) requestAttributes;
    }

    /**
     * 获取request对象
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest()
    {
        return getRequestAttributes().getRequest();
    }

    /**
     * 获取response对象
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse()
    {
        return getRequestAttributes().getResponse();
    }


}
