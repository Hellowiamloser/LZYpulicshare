package com.hgws.sbp.modules.system.dictionary.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.modules.system.dictionary.dao.DictionaryDetailDao;
import com.hgws.sbp.modules.system.dictionary.entity.Dictionary;
import com.hgws.sbp.modules.system.dictionary.entity.DictionaryDetail;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-01-18 11:30
 * @description: 系统管理-数据字典逻辑层
 */
@Service
@CacheConfig(cacheNames = "system:dictionary:detail")
public class DictionaryDetailService extends BaseService<DictionaryDetailDao, DictionaryDetail> {

    /**
     * 根据数据字典主键查询详情表
     * @param id 数据字典主键
     * @return 数据字典详情集合
     */
    @Cacheable
    public List<DictionaryDetail> loadDetailById(int id)
    {
        QueryWrapper<DictionaryDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("dictionary_id", id);
        return dao.selectList(wrapper);
    }

    /**
     * 根据数据字典编码查询详情表
     * @param code 数据字典编码
     * @return 数据字典详情集合
     */
    @Cacheable
    public List<Map<String, Object>> loadDetailByCode(String code)
    {
        return dao.loadDetailByCode(code);
    }

    /**
     * 根据数据字典编码与值查询名称
     * @param code 数据字典编码
     * @param value 数据字典详情值
     * @return 数据字典详情表现值
     */
    @Cacheable
    public String loadDetailNameByCode(String code, int value) {
        return dao.loadDetailNameByCode(code, value);
    }

    /**
     * 添加
     * @param entity 属性
     * @return 条数
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int save(DictionaryDetail entity)
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
