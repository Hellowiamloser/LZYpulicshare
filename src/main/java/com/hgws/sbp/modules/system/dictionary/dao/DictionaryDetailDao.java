package com.hgws.sbp.modules.system.dictionary.dao;

import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.modules.system.dictionary.entity.DictionaryDetail;

import java.util.List;
import java.util.Map;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project rapid-development-platform
 * @datetime 2022-01-18 11:30
 * @description: 系统管理-数据字典数据层
 */
public interface DictionaryDetailDao extends BaseDao<DictionaryDetail> {
    List<Map<String, Object>> loadDetailByCode(String code);
}
