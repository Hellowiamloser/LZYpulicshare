package com.hgws.sbp.modules.light.group.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgws.sbp.commons.annotation.LoggerOperation;
import com.hgws.sbp.commons.base.result.Result;
import com.hgws.sbp.commons.enumerate.TypeEnumerate;
import com.hgws.sbp.modules.light.group.entity.Group;
import com.hgws.sbp.modules.light.group.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;
import java.util.Collection;


@Api(tags = "[分组管理]")
@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @PostMapping
    @ApiOperation("分组添加")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupOrder",value = "序号",required = true,dataTypeClass =Integer.class),
            @ApiImplicitParam(name = "groupId",value = "分组编号",required = true,dataTypeClass =String.class),
            @ApiImplicitParam(name = "groupName",value = "分组名称",required = true,dataTypeClass =String.class),
            @ApiImplicitParam(name = "groupRemark",value = "分组备注",dataTypeClass =String.class)
    })
    @LoggerOperation(module = "分组管理",message = "分组添加",type = TypeEnumerate.INSERT)
    public Result saveGroup(@RequestBody @ApiIgnore @Validated(Insert.class) Group entity){
       return groupService.insert(entity);
    }

    @PutMapping
    @ApiOperation("分组编辑")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "groupOrder",value = "序号",required = true,dataTypeClass =Integer.class),
            @ApiImplicitParam(name = "groupId",value = "分组编号",required = true,dataTypeClass =String.class),
            @ApiImplicitParam(name = "groupName",value = "分组名称",required = true,dataTypeClass =String.class),
            @ApiImplicitParam(name = "groupRemark",value = "分组备注",dataTypeClass =String.class)
    })
    public Result updateGroup(@RequestBody @ApiIgnore @Validated(Update.class) Group entity){
        return groupService.update(entity);
    }

    @DeleteMapping("{id}")
    @ApiOperation("分组删除")
    public void delete(@PathVariable int id){
        groupService.delete(id);
    }

    @DeleteMapping
    @ApiOperation("分组批删")
    public void delete(@RequestBody Collection<Integer> ids) {
        groupService.deletes(ids);
    }

    @GetMapping("page")
    @ApiOperation("分页查询")
    public Page<Group> page(@RequestBody Group entity){
       return groupService.page(entity);
    }

}
