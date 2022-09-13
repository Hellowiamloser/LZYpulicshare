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
package com.hgws.sbp.modules.system.user.dao;

import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.modules.system.user.entity.SystemUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-29 17:36
 * @description: 系统·用户·数据
 */
public interface SystemUserDao extends BaseDao<SystemUser> {
    /**
     * 根据登陆账号查询用户权限列表
     * @param username 登陆账号
     * @return 权限列表
     */
    @Select("select distinct a.code from system_authority a, system_role_authority ra " +
            "   where a.id = ra.authority_id and ra.role_id in " +
            "       (select role_id from system_user_role where user_id = " +
            "           (select id from system_user where name = #{username}))")
    List<String> loadUserAuthorities(String username);
}
