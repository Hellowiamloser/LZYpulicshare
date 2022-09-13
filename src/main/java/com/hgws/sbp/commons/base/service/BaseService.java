package com.hgws.sbp.commons.base.service;

import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.commons.base.entity.Base;
import com.hgws.sbp.commons.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-29 17:29
 * @description: 公共逻辑层实例
 */
@Transactional(
    timeout = 60,
    readOnly = true,
    rollbackFor = Exception.class,
    noRollbackFor = RuntimeException.class
)
public abstract class BaseService<D extends BaseDao<T>, T extends Base> {

    /**
     * 当前模块数据层代理实现类
     */
    @Autowired
    public D dao;

    @Autowired
    public UserUtils userUtils;

    /**
     * 获取当前模块数据层实现
     * @return D
     */
    public D getDao() {
        return this.dao;
    }

}
