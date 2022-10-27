package com.hgws.sbp.modules.system.dictionary.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgws.sbp.commons.annotation.LoggerOperation;
import com.hgws.sbp.commons.enumerate.TypeEnumerate;
import com.hgws.sbp.modules.system.dictionary.entity.Dictionary;
import com.hgws.sbp.modules.system.dictionary.entity.DictionaryDetail;
import com.hgws.sbp.modules.system.dictionary.service.DictionaryDetailService;
import com.hgws.sbp.modules.system.dictionary.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project rapid-development-platform
 * @datetime 2022-01-18 11:30
 * @description: 系统管理-数据字典表现层
 */
@RestController
@Api(tags = "[数据字典接口]")
@RequestMapping("system/dictionary")
public class DictionaryController {

    @Autowired
    private DictionaryService dictionaryService;
    @Autowired
    private DictionaryDetailService dictionaryDetailService;

    @PostMapping("page")
    @ApiOperation("数据字典分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "字典名称", dataTypeClass = String.class),
        @ApiImplicitParam(name = "code", value = "字典编号", dataTypeClass = String.class)
    })
    @LoggerOperation(module = "系统-数据字典", message = "数据字典分页查询")
    public Page<Dictionary> page(@RequestBody @ApiIgnore Dictionary entity)
    {
        return dictionaryService.page(entity);
    }

    @PostMapping
    @ApiOperation("数据字典添加")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "字典名称", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "code", value = "字典编号", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "reason", value = "字典备注", required = true, dataTypeClass = String.class)
    })
    @LoggerOperation(module = "系统-数据字典", message = "数据字典添加", type = TypeEnumerate.INSERT)
    public void save(@RequestBody @ApiIgnore @Valid Dictionary entity)
    {
        dictionaryService.save(entity);
    }

    @PostMapping("detail")
    @ApiOperation("数据字典详情添加")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "字典名称", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "code", value = "字典数值", required = true, dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "sorted", value = "字典排序", required = true, dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "reason", value = "字典备注", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "dictionaryId", value = "数据字典外键", required = true, dataTypeClass = String.class)
    })
    @LoggerOperation(module = "系统-数据字典", message = "数据字典详情添加", type = TypeEnumerate.INSERT)
    public void save(@RequestBody @ApiIgnore @Valid DictionaryDetail entity)
    {
        dictionaryDetailService.save(entity);
    }

    @GetMapping("detail/{id}")
    @ApiOperation("数据字典详情查询_字典外键")
    @LoggerOperation(module = "系统-数据字典", message = "数据字典详情查询_字典外键", type = TypeEnumerate.SELECT)
    public List<DictionaryDetail> list(@PathVariable int id)
    {
        return dictionaryDetailService.loadDetailById(id);
    }

    @GetMapping("detail/code/{code}")
    @ApiOperation("数据字典详情查询_字典编码")
    @LoggerOperation(module = "系统-数据字典", message = "数据字典详情查询_字典编码", type = TypeEnumerate.SELECT)
    public List<Map<String, Object>> list(@PathVariable String code)
    {
        return dictionaryDetailService.loadDetailByCode(code);
    }

    @DeleteMapping("{id}")
    @ApiOperation("删除数据字典详")
    @LoggerOperation(module = "系统-数据字典", message = "删除数据字典详", type = TypeEnumerate.DELETE)
    public void delete(@PathVariable int id)
    {
        dictionaryService.delete(id);
    }

    @DeleteMapping("detail/{id}")
    @ApiOperation("删除数据字典详情")
    @LoggerOperation(module = "系统-数据字典", message = "删除数据字典详情", type = TypeEnumerate.DELETE)
    public void deleteDetail(@PathVariable int id)
    {
        dictionaryDetailService.delete(id);
    }

}
