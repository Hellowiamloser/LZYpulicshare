package com.hgws.sbp.modules.system.logs.service;

import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.commons.utils.ServletUtils;
import com.hgws.sbp.modules.system.logs.dao.LogsDao;
import com.hgws.sbp.modules.system.logs.entity.Logs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-07 14:13
 * @description: 日志逻辑
 */
@Service
public class LogsService extends BaseService<LogsDao, Logs> {

    /**
     * 日志记录
     * @param times     耗时
     * @param module    模块
     * @param type      类型
     * @param message   消息
     */
    @Transactional
    public void insert(long times, String module, String type, String message, String params, String result)
    {
        HttpServletRequest request = ServletUtils.getRequest();

        Logs entity = new Logs();
        entity.setModule(module);
        entity.setType(type);
        entity.setMessage(message);
        entity.setExecuteParams(params);
        entity.setReturnValue(result);
        entity.setRequestMethod(request.getMethod());
        entity.setRequestIpaddr(request.getRemoteAddr());
        entity.setRequestUrl(request.getRequestURL().toString());
        entity.setExecuteTime(times);

        dao.insert(entity);
    }

}
