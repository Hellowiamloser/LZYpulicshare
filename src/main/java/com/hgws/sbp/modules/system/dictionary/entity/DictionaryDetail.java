package com.hgws.sbp.modules.system.dictionary.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hgws.sbp.commons.base.entity.Base;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-01-18 11:30
 * @description: 系统管理-数据字典实体类
 */
@Getter
@Setter
@TableName("system_data_dictionary_detail")
public class DictionaryDetail extends Base {
    @ApiModelProperty(name = "name", value = "字典名称(表现值)", required = true, dataType = "String")
    @NotBlank(message = "字典名称不允许为空")
    @Length(min = 1, max = 60, message = "字典名称长度要求在{min}-{max}之间")
    private String name;

    @ApiModelProperty(name = "code", value = "字典数值(真实值)", required = true, dataType = "Integer")
    @NotNull(message = "字典数值不允许为空")
    private Integer code;

    @ApiModelProperty(name = "sorted", value = "字典排序", required = true, dataType = "Integer")
    @NotNull(message = "字典排序不允许为空")
    private Integer sorted;

    @ApiModelProperty(name = "reason", value = "字典备注", required = true, dataType = "String")
    @NotBlank(message = "字典备注不允许为空")
    private String reason;

    @ApiModelProperty(name = "dictionaryId", value = "数据字典外键", required = true, dataType = "Integer")
    @TableField(value = "dictionary_id")
    @NotNull(message = "字典外键不允许为空")
    private Integer dictionaryId;
}
