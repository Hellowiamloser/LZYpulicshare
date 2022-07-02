package com.hgws.sbp.components.result;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.utils.ServletUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-02 11:12
 * @description: 自定义返回JSON响应
 */
public class ResponseResult {
    /**
     * 封装响应状态
     * @param message 消息
     */
    public static void result(Result message)
    {
        result(message, 200);
    }

    /**
     * 封装响应状态
     * @param message   消息
     * @param code      状态码
     */
    public static void result(Result message, int code)
    {
        HttpServletResponse response = ServletUtils.getResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        String json = null;
        try {
            json = objectMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(code);

        PrintWriter out = null;
        try {
            out = response.getWriter();
            assert json != null;
            out.write(json);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            assert out != null;
            out.close();
        }
    }
}
