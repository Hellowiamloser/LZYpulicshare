package com.hgws.sbp.modules.system.logs.advice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hgws.sbp.commons.annotation.LoggerOperation;
import com.hgws.sbp.modules.system.logs.service.LogsService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-01 15:56
 * @description: TODO
 */
@Aspect
@Component
public class LogsAdvice {

    @Autowired
    private LogsService logsService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 切入点  LoggerOperation
     */
    @Pointcut("@annotation(com.hgws.sbp.commons.annotation.LoggerOperation)")
    public void pointcut(){}

    /**
     * AOP拦截日志功能
     * @param joinPoint 连接点
     * @return  返回结果
     * @throws Throwable 异常
     */
    @Around("pointcut()")
    public Object logger(ProceedingJoinPoint joinPoint) throws Throwable {
        //开始计时
        long start = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        //结束计时
        long end = System.currentTimeMillis();
        //获取注解类
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        //获取操作类型
        LoggerOperation annotation = signature.getMethod().getAnnotation(LoggerOperation.class);
        //获取接口参数
        Object[] args = joinPoint.getArgs();
        String params = "无参数";
        if(!ObjectUtils.isEmpty(args))
            params = objectMapper.writeValueAsString(args);
        String result = "无结果";
        if(!ObjectUtils.isEmpty(object))
            result = objectMapper.writeValueAsString(object);
        logsService.insert(end-start, annotation.module(), annotation.type().getValue(), annotation.message(), params, result);
        return object;
    }

}
