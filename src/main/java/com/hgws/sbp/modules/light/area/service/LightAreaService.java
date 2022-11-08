package com.hgws.sbp.modules.light.area.service;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.commons.enumerate.ResultEnumerate;
import com.hgws.sbp.modules.light.area.dao.LightAreaDao;
import com.hgws.sbp.modules.light.area.entity.LightArea;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "light:area")//为类下所有的方法分配缓存空间
public class LightAreaService extends BaseService<LightAreaDao, LightArea> {//BaseService<D,T>其中D代表数据层接口,T代表实体类

    @Transactional
    //单个删除
    @CacheEvict(allEntries = true)//调用方法会清除缓存
    public void delete(int id) {
        queryIdByPid(id);
    }

    @Transactional
    //批量删除
    public void deletes(Collection<Integer> ids) {
        ids.forEach(this::queryIdByPid);
    }

    //遍历并删除给定地区id的子地区方法
    void queryIdByPid(int id){
        List<Integer> result = dao.queryIdByPid(id);
        if(!ObjectUtils.isEmpty(result)){
            result.forEach(this::queryIdByPid);
        }
        dao.deleteById(id);//底层封装好了,会删除查询到的栈
    }


    //遍历查询
    @Transactional
    @Cacheable//结果集会开启缓存
    public List<Map<String,Object>> queryTree(int id){
        return dao.queryTree(id);
    }


    @Transactional
    //添加地区
    public Result save(LightArea entity){
        //1.查询区域名称的唯一性
        int result = dao.queryNameByPid(entity.getId(), entity.getAreaName());
        //2.添加城市区域信息
        if(result==0){
            dao.insert(entity);
            return Result.success();
        }
        return Result.failure(ResultEnumerate.INTERFACE_CALL_ERROR);
    }

    //根据主键查询地区
    public LightArea selectById(int id) {
        return dao.selectById(id);
    }
}
