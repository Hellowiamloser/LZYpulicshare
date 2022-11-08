package com.hgws.sbp.modules.light.area.controller;

import com.hgws.sbp.commons.annotation.AccessOperation;
import com.hgws.sbp.commons.annotation.LoggerOperation;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.enumerate.TypeEnumerate;
import com.hgws.sbp.commons.validator.group.Insert;
import com.hgws.sbp.modules.light.area.entity.LightArea;
import com.hgws.sbp.modules.light.area.service.LightAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Api(tags = "[灯具-区域]")
@RestController
@RequestMapping("light/area")
public class LightAreaController {
    @Autowired
    private LightAreaService lightAreaService;

    @PostMapping
    @ApiOperation("区域添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaCode",value = "区域编号",required = true,dataTypeClass =String.class),
            @ApiImplicitParam(name = "areaName",value = "区域名称",required = true,dataTypeClass =String.class),
            @ApiImplicitParam(name = "areaLevel",value = "区域等级",required = true,dataTypeClass =Integer.class),
            @ApiImplicitParam(name = "areaPid",value ="上级区域",required = true,dataTypeClass =Integer.class),
            @ApiImplicitParam(name = "areaNote",value = "区域备注",required = true,dataTypeClass =String.class),
    })
    @LoggerOperation(module = "区域",message = "区域添加",type = TypeEnumerate.INSERT)
    public Result save(@RequestBody @ApiIgnore @Validated(Insert.class) LightArea entity){
        //@Validated(Update.class)更新时校验,
        // @Validated(Insert.class)插入校验
       return lightAreaService.save(entity);
    }

    @PutMapping
    @ApiOperation("区域修改")
    public Result update(@RequestBody  LightArea entity){
        return lightAreaService.update(entity);
    }

    @DeleteMapping("{id}")
    @ApiOperation("区域删除")
    public void delete(@PathVariable int id){
        lightAreaService.delete(id);
    }

    @DeleteMapping
    @ApiOperation("区域批删")
    public void delete(@RequestBody Collection<Integer> ids) {
        lightAreaService.deletes(ids);
    }

    @GetMapping("tree")
    @ApiOperation("区域查询")
    @AccessOperation
    public List<Map<String,Object>> tree(){
        return lightAreaService.queryTree(0);
    }

    @GetMapping("{id}")
    @ApiOperation("根据区域主键查询")//根据主键查询id
    public LightArea load(@PathVariable int id){
        return lightAreaService.selectById(id);
    }
}
