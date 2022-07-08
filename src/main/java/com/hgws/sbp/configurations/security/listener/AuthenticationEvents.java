package com.hgws.sbp.configurations.security.listener;

import com.hgws.sbp.commons.enumerate.ResultEnumerate;
import com.hgws.sbp.commons.enumerate.TypeEnumerate;
import com.hgws.sbp.modules.system.logs.service.LogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-08 13:56
 * @description: 登陆认证监听器
 */
@Component
public class AuthenticationEvents {

    @Autowired
    private LogsService logsService;

    /**
     * 监听认证成功
     * @param events success
     */
    @EventListener
    public void success(AuthenticationSuccessEvent events) {
        // 获取认证账号
        String username = events.getAuthentication().getName();
        // 写入登陆日志
        logsService.insert(0, "登陆成功", TypeEnumerate.LOGIN.getValue(), ResultEnumerate.LOGIN_SUCCESS.getMessage(), username, null);
    }

    /**
     * 监听认证异常
     * @param events failure
     */
    @EventListener
    public void failure(AbstractAuthenticationFailureEvent events) {
        // 获取认证异常
        Exception exception = events.getException();
        // 获取认证账号
        String username = events.getAuthentication().getName();
        // 判断后写入登陆日志
        if(exception instanceof UsernameNotFoundException) {
            logsService.insert(0, "登陆失败", TypeEnumerate.LOGIN.getValue(), ResultEnumerate.LOGIN_USER_NOT_EXIST.getMessage(), username, null);
        } else if(exception instanceof BadCredentialsException) {
            logsService.insert(0, "登陆失败", TypeEnumerate.LOGIN.getValue(), ResultEnumerate.LOGIN_PASS_INPUT_ERROR.getMessage(), username, null);
        } else if(exception instanceof LockedException) {
            logsService.insert(0, "登陆失败", TypeEnumerate.LOGIN.getValue(), ResultEnumerate.LOGIN_USER_LOCKED.getMessage(), username, null);
        } else {
            logsService.insert(0, "登陆失败", TypeEnumerate.LOGIN.getValue(), ResultEnumerate.LOGIN_OTHER_ERROR.getMessage(), username, null);
        }
    }

}
