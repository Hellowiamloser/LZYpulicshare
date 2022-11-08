package com.hgws.sbp.modules.light.strategy.controller;
import com.hgws.sbp.modules.light.strategy.entity.Strategy;
import com.hgws.sbp.modules.light.strategy.service.StrategyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "[策略管理]")
@RestController
@RequestMapping("light/strategy")
public class StrategyController{

    @Autowired
    private StrategyService strategyService;

    @PostMapping
    @ApiOperation("策略添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "strategyId",value = "策略编号",required = true,dataTypeClass =String.class),
            @ApiImplicitParam(name = "strategyName",value = "策略名称",required = true,dataTypeClass =String.class),
            @ApiImplicitParam(name = "upState",value = "启用状态",required = true,dataTypeClass =Integer.class)
    })
    public void save(@RequestBody @ApiIgnore Strategy entity){
        strategyService.save(entity);
    }

    @GetMapping("page")
    public List<Strategy> queryPage(Strategy entity){
        return strategyService.queryPage(entity);
    }

}
