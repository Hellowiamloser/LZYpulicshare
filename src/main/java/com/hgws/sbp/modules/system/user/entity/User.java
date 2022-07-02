package com.hgws.sbp.modules.system.user.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hgws.sbp.commons.base.entity.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-29 17:36
 * @description: TODO
 */
@Getter
@Setter
@TableName(value = "system_user")
@ApiModel("[系统·用户·属性]")
public class User extends Base {

    @NotBlank(message = "账号不允许为空")
    @Length(min = 2, max = 15, message = "账号长度要求在{min}-{max}之间")
    @ApiModelProperty(name = "name", value = "登陆账号", required = true, dataType = "String")
    private String name;

    @NotBlank(message = "密码不允许为空")
    @Length(min = 6, max = 16, message = "登陆密码长度要求在{min}-{max}之间")
    //@Password(message = "登陆密码强度太低风险太高")
    private String pass;

    @NotNull(message = "性别不允许为空")
    @Range(min = 1, max = 2, message = "请选择正确性别")
    @ApiModelProperty(name = "gender", value = "用户性别", required = true, dataType = "String")
    private Integer gender;

    @NotBlank(message = "昵称不允许为空")
    @Length(min = 2, max = 15, message = "昵称长度要求在{min}-{max}之间")
    @ApiModelProperty(name = "nick", value = "昵称", required = true, dataType = "String")
    private String nick;

    @NotBlank(message = "手机号码不允许为空")
    @Pattern(
        regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",
        message = "请输入正确的手机号码"
    )
    @ApiModelProperty(name = "phone", value = "手机号码", required = true, dataType = "String")
    private String phone;

    @Email(message = "电子邮箱必须符合规范")
    @Length(min = 6, max = 35, message = "电子邮箱长度要求在{min}-{max}之间")
    @ApiModelProperty(name = "email", value = "电子邮箱", required = true, dataType = "String")
    private String email;

    @NotNull(message = "出生日期不允许为空")
    @Past(message = "出生日期必须是当前日期之前")
    @ApiModelProperty(name = "birthday", value = "出生日期", required = true, dataType = "Date")
    private LocalDate birthday;

    @NotBlank(message = "家庭住址不允许为空")
    @Length(min = 2, max = 60, message = "家庭住址长度要求在{min}-{max}之间")
    @ApiModelProperty(name = "address", value = "家庭住址", required = true, dataType = "String")
    private String address;

    @Length(min = 2, max = 900, message = "个人介绍长度要求在{min}-{max}之间")
    @ApiModelProperty(name = "remarks", value = "个人介绍", required = true, dataType = "String")
    private String remarks;

    @ApiModelProperty(name = "locked", value = "0:启用 1:锁定", required = true, dataType = "Integer")
    private Integer locked = 0;
}
