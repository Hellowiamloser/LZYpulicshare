package com.hgws.sbp.modules.light.area.dao;
import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.modules.light.area.entity.LightArea;

import java.util.List;
import java.util.Map;


/**
 * BaseDao<T>中的范型选择实体类LightArea
 */
public interface LightAreaDao extends BaseDao<LightArea> {

    List<Integer> queryIdByPid(int id);//根据外键进行查询

    List<Map<String,Object>> queryTree(int id);//遍历

    int queryNameByPid(int id, String name);//
}
