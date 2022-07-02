package com.hgws.sbp.modules.system.user.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hgws.sbp.commons.annotation.LoggerOperation;
import com.hgws.sbp.commons.base.controller.BaseController;
import com.hgws.sbp.commons.enumerate.TypeEnumerate;
import com.hgws.sbp.modules.system.user.entity.User;
import com.hgws.sbp.modules.system.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Date;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-30 16:44
 * @description: TODO
 */
@Api(tags = "[系统·用户·接口]")
@RestController
@RequestMapping("system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping
    @LoggerOperation(module = "系统-用户", message = "用户注册修改", type = TypeEnumerate.INSERT)
    @ApiOperation("注册用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "登陆账号", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "pass", value = "登陆密码", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "gender", value = "用户性别", required = true, dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "nick", value = "用户昵称", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "email", value = "电子邮箱", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "birthday", value = "出生日期", required = true, dataTypeClass = Date.class),
        @ApiImplicitParam(name = "address", value = "家庭住址", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "remarks", value = "备注信息", dataTypeClass = String.class)
    })
    public Integer save(@RequestBody @Validated User entity)
    {
        return userService.insert(entity);
    }


    @PutMapping
    @LoggerOperation(module = "系统-用户", message = "用户注册修改", type = TypeEnumerate.UPDATE)
    @ApiOperation("编辑用户")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "id", value = "用户主键", required = true, dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "name", value = "登陆账号", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "pass", value = "登陆密码", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "gender", value = "用户性别", required = true, dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "nick", value = "用户昵称", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "phone", value = "手机号码", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "email", value = "电子邮箱", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "birthday", value = "出生日期", required = true, dataTypeClass = Date.class),
        @ApiImplicitParam(name = "address", value = "家庭住址", required = true, dataTypeClass = String.class),
        @ApiImplicitParam(name = "remarks", value = "备注信息", dataTypeClass = String.class),
        @ApiImplicitParam(name = "revision", value = "乐观锁", required = true, dataTypeClass = Integer.class)
    })
    public Integer update(@RequestBody @Validated User entity)
    {
        return userService.update(entity);
    }

    @DeleteMapping("{id}")
    @LoggerOperation(module = "系统-用户", message = "用户主键查询", type = TypeEnumerate.DELETE)
    @ApiOperation("删除用户")
    @ApiImplicitParam(name = "id", value = "用户主键ID", dataTypeClass = Integer.class)
    public Integer delete(@PathVariable int id)
    {
        return userService.delete(id);
    }

    @GetMapping("{id}")
    @LoggerOperation(module = "系统-用户", message = "用户主键查询")
    @ApiOperation("ID查询用户")
    @ApiImplicitParam(name = "id", value = "用户主键ID", dataTypeClass = Integer.class)
    public User load(@PathVariable int id)
    {
        return userService.load(id);
    }

    @PostMapping("page")
    @LoggerOperation(module = "系统-用户", message = "用户分页查询")
    @ApiOperation("条件分页查询")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "name", value = "姓名", dataTypeClass = String.class),
        @ApiImplicitParam(name = "nick", value = "昵称", dataTypeClass = String.class),
        @ApiImplicitParam(name = "pageNo", value = "当前页: 默认1", dataTypeClass = Integer.class),
        @ApiImplicitParam(name = "pageSize", value = "每页条数: 默认10", dataTypeClass = Integer.class)
    })
    public Page<User> page(@RequestBody @ApiIgnore User entity)
    {
        return userService.page(entity);
    }

}
