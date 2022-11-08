package com.hgws.sbp.modules.light.group.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.modules.light.group.dao.GroupDao;
import com.hgws.sbp.modules.light.group.entity.Group;
import org.springframework.cache.annotation.CacheEvict;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.List;

@Service
public class GroupService extends BaseService<GroupDao, Group> {

    @Transactional
    public void delete(int id) {
        dao.deleteById(id);
    }

    @Transactional
    public void deletes(Collection<Integer> ids) {
        for (Integer id : ids) {
            dao.deleteById(id);
        }
    }

    public Page<Group> page(Group entity){
        //1.分页mybatis的Page方法
        Page<Group> page = Page.of(entity.getPageNo(), entity.getPageSize());
        //2.条件控制
        QueryWrapper<Group> wrapper = new QueryWrapper<Group>();
        //3.判断分页是否包含指定条件,分组编号
        if(StringUtils.hasLength(entity.getGroupCode())){
//          wrapper.eq("",entity.getGroupId());//精确查询分组编号,wrapper.eq("数据库字段",实体类属性)

            wrapper.like("group_code",entity.getGroupCode());//模糊查询分组编号,wrapper.like("数据库字段",实体类属性)
        }
        //4.判断分页是否包含指定条件,分组名称
        if(StringUtils.hasLength(entity.getGroupName())){
            wrapper.like("group_name",entity.getGroupName());//模糊查询分组名称,wrapper.like("数据库字段",实体类属性)
        }
        return dao.selectPage(page,wrapper);
    }

}
