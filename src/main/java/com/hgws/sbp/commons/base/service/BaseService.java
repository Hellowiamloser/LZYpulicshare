package com.hgws.sbp.commons.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.commons.base.entity.Base;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.enumerate.ResultEnumerate;
import com.hgws.sbp.commons.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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

    /**
     * 添加
     * @param entity
     * @return
     */
    @Transactional
    public Result insert(T entity)
    {
        int result = dao.insert(entity);
        if(result == 0) {
            return Result.failure(ResultEnumerate.INTERFACE_CALL_ERROR);
        }
        return Result.success();
    }

    /**
     * 修改
     * @param entity
     * @return
     */
    @Transactional
    public Result update(T entity)
    {
        int result = dao.updateById(entity);
        if(result == 0) {
            return Result.failure(ResultEnumerate.INTERFACE_CALL_ERROR);
        }
        return Result.success();
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Transactional
    public Result delete(Serializable id)
    {
        int result = dao.deleteById(id);
        if(result == 0) {
            return Result.failure(ResultEnumerate.INTERFACE_CALL_ERROR);
        }
        return Result.success();
    }

    /**
     * 批量删除
     * @param ids
     * @return
     */
    @Transactional
    public Result delete(Collection<Serializable> ids)
    {
        int result = dao.deleteBatchIds(ids);
        if(result == 0) {
            return Result.failure(ResultEnumerate.INTERFACE_CALL_ERROR);
        }
        return Result.success();
    }

    /**
     * 根据元素属性查询元素结果集
     * @param wrapper 元素条件
     * @return 元素结果集
     */
    public List<T> queryEntity(QueryWrapper<T> wrapper)
    {
        return dao.selectList(wrapper);
    }

    /**
     * 根据条件查询结果集
     * @param wrapper 元素条件
     * @return 元素结果集
     */
    public List<Map<String, Object>> queryMaps(QueryWrapper<T> wrapper)
    {
        return dao.selectMaps(wrapper);
    }

}
