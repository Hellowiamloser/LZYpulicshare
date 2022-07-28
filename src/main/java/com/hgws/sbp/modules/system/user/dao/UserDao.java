package com.hgws.sbp.modules.system.user.dao;

import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.modules.system.user.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-29 17:36
 * @description: 系统·用户·数据
 */
public interface UserDao extends BaseDao<User> {
    @Select("select distinct a.code from system_authority a, system_role_authority ra " +
            "   where a.id = ra.authority_id and ra.role_id in " +
            "       (select role_id from system_user_role where user_id = " +
            "           (select id from system_user where name = #{username}))")
    List<String> loadUserAuthorities(String username);
}
