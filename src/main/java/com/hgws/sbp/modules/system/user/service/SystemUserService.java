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
package com.hgws.sbp.modules.system.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.modules.system.user.dao.SystemUserDao;
import com.hgws.sbp.modules.system.user.entity.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-29 17:36
 * @description: 系统·用户·逻辑
 */
@Service
@CacheConfig(cacheNames = "system:user")
public class SystemUserService extends BaseService<SystemUserDao, SystemUser> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 添加人员
     * @param entity 人员属性
     * @return 执行状态
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int insert(SystemUser entity)
    {
        entity.setPass(passwordEncoder.encode(entity.getPass()));
        return dao.insert(entity);
    }

    /**
     * 修改人员
     * @param entity 人员属性
     * @return 执行状态
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int update(SystemUser entity)
    {
        return dao.updateById(entity);
    }

    /**
     * 删除人员
     * @param id 人员主键
     * @return 执行状态
     */
    @Transactional
    @CacheEvict(allEntries = true)
    public int delete(int id) {
        return dao.deleteById(id);
    }

    /**
     * 主键查询
     * @param id 人员主键
     * @return 人员属性
     */
    @Cacheable
    public SystemUser load(int id) {
        return dao.selectById(id);
    }

    /**
     * 用户分页查询
     * @param entity 实体
     * @return 分页对象
     */
    @Cacheable(key = "'page:no:'+#entity.pageNo+':size:'+#entity.pageSize")
    public Page<SystemUser> page(SystemUser entity)
    {
        //分页对象
        Page<SystemUser> userPage = Page.of(entity.getPageNo(), entity.getPageSize());
        //动态查询条件
        QueryWrapper<SystemUser> queryWrapper = new QueryWrapper<>();
        //账号精确查询
        if(StringUtils.hasLength(entity.getName()))
        {
            queryWrapper.eq("name", entity.getName());
        }
        //昵称模糊查询
        if(StringUtils.hasLength(entity.getNick()))
        {
            queryWrapper.like("nick", entity.getNick());
        }
        //启用禁用状态
        if(!ObjectUtils.isEmpty(entity.getLocked()))
        {
            queryWrapper.eq("locked", entity.getLocked());
        }
        //电话精确查询
        if(StringUtils.hasLength(entity.getPhone()))
        {
            queryWrapper.eq("phone", entity.getPhone());
        }
        //邮箱精确查询
        if(StringUtils.hasLength(entity.getEmail()))
        {
            queryWrapper.eq("email", entity.getEmail());
        }
        //出生日期查询
        if(!ObjectUtils.isEmpty(entity.getBirthday()))
        {
            queryWrapper.eq("birthday", entity.getBirthday());
        }
        return dao.selectPage(userPage, queryWrapper);
    }

    /**
     * 根据登录账号查询用户属性
     * @param username 登陆账号
     * @return 用户属性
     */
    @Cacheable
    public SystemUser loadUserByUsername(String username)
    {
        QueryWrapper<SystemUser> wrapper = new QueryWrapper<>();
        wrapper.eq("name", username);
        return dao.selectOne(wrapper);
    }

    /**
     * 根据登陆账号查询用户权限
     * @param username 登陆账号
     * @return 用户权限
     */
    @Cacheable
    public List<String> loadUserAuthorities(String username)
    {
        return dao.loadUserAuthorities(username);
    }

}
