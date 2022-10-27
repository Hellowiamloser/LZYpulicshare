package com.hgws.sbp.modules.system.dictionary.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.modules.system.dictionary.dao.DictionaryDao;
import com.hgws.sbp.modules.system.dictionary.entity.Dictionary;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-01-18 11:30
 * @description: 系统管理-数据字典逻辑层
 */
@Service
@CacheConfig(cacheNames = "system:dictionary")
public class DictionaryService extends BaseService<DictionaryDao, Dictionary> {

    /**
     * 按条件分页查询
     * @param entity 字典信息
     * @return 分页对象
     */
    @Cacheable(key = "'page:no:'+#entity.pageNo+':size:'+#entity.pageSize")
    public Page<Dictionary> page(Dictionary entity)
    {
        QueryWrapper<Dictionary> wrapper = new QueryWrapper<>();
        if(StringUtils.hasLength(entity.getName()))
        {
            wrapper.like("name", entity.getName());
        }
        if(StringUtils.hasLength(entity.getCode()))
        {
            wrapper.like("code", entity.getCode());
        }
        return dao.selectPage(Page.of(entity.getPageNo(), entity.getPageSize()), wrapper);
    }

    /**
     * 添加
     * @param entity 属性
     * @return 条数
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int save(Dictionary entity)
    {
        return dao.insert(entity);
    }

    /**
     * 根据主键ID删除
     * @param id 主键ID
     * @return 条数
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int delete(int id)
    {
        return dao.deleteById(id);
    }

}
