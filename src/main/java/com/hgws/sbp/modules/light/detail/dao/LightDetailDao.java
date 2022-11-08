package com.hgws.sbp.modules.light.detail.dao;

import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.modules.light.detail.entity.Detial;
import com.hgws.sbp.modules.light.group.entity.Group;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface LightDetailDao extends BaseDao<Detial> {

    public List<Detial> query();

    public int insertDetail(Detial entity);

    public int insertGroupDetail(@Param("id") int groupId, List<Group> list);//根据中间表：一个id,一个集合多个分组Id

}
