package com.hgws.sbp.modules.light.strategy.dao;

import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.modules.light.strategy.entity.Scheduled;
import com.hgws.sbp.modules.light.strategy.entity.Strategy;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface StrategyDao extends BaseDao<Strategy> {
    public int insert(Strategy entity);
    public int insertScheduled(Scheduled entity);
    public List<Strategy> queryPage(Strategy entity);
    public List<Scheduled> queryScheduled(String strategyCode);
}
