/*
* Copyright (c) [2022] [zhouhonggang]
*
* [spring-boot-pro] is licensed under Mulan PSL v2.
* You can use this software according to the terms and conditions of the Mulan PSL v2.
* You may obtain a copy of Mulan PSL v2 at:
*
*  http://license.coscl.org.cn/MulanPSL2
*
* THIS SOFTWARE IS PROVIDED ON AN "AS IS" BASIS, WITHOUT WARRANTIES OF ANY KIND,
* EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO NON-INFRINGEMENT,
* MERCHANTABILITY OR FIT FOR A PARTICULAR PURPOSE.
* See the Mulan PSL v2 for more details.
*/
package ${package.Service};

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${superServiceImplClassPackage};

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* @author zhouhonggang
* @version 1.0.0
* @project spring-boot-pro
* @datetime ${date}
* @description: [${table.comment!}·逻辑]
*/
@Service
@CacheConfig(cacheNames = "${controllerMappingHyphen?replace('-', ':')}")
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}>{

    /**
     * 添加${table.comment!}
     * @param entity ${table.comment!}属性
     * @return 执行状态
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int insert(${entity} entity)
    {
        return dao.insert(entity);
    }

    /**
     * 修改${table.comment!}
     * @param entity ${table.comment!}属性
     * @return 执行状态
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int update(${entity} entity)
    {
        return dao.updateById(entity);
    }

    /**
     * 删除${table.comment!}
     * @param id ${table.comment!}主键
     * @return 执行状态
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int delete(int id) {
        return dao.deleteById(id);
    }

    /**
     * 主键查询
     * @param id ${table.comment!}主键
     * @return ${table.comment!}属性
     */
    @Cacheable
    public ${entity} load(int id) {
        return dao.selectById(id);
    }

    /**
     * 根据条件分页查询
     * @param entity 实体对象
     * @return 分页对象
     */
    public Page<${entity}> page(${entity} entity)
    {
        //封装分页参数
        Page<${entity}> page = Page.of(entity.getPageNo(), entity.getPageSize());
        //封装查询参数
        QueryWrapper<${entity}> wrapper = new QueryWrapper<>();
        //假如name有值则执行查询
        //if(StringUtils.hasLength(entity.getName())) {
        //    wrapper.like("name", entity.getName());
        //}
        //执行分页条件查询
        return dao.selectPage(page, wrapper);
    }

}
