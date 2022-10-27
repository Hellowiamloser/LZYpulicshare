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
import java.util.List;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project rapid-development-platform
 * @datetime 2022-01-18 11:30
 * @description: 系统管理-数据字典实体类
 */
@Getter
@Setter
@TableName("system_data_dictionary")
public class Dictionary extends Base {
    @ApiModelProperty(name = "name", value = "字典名称", required = true, dataType = "String")
    @NotBlank(message = "字典名称不允许为空")
    @Length(min = 2, max = 60, message = "字典名称长度要求在{min}-{max}之间")
    private String name;

    @ApiModelProperty(name = "code", value = "字典编号", required = true, dataType = "String")
    @NotBlank(message = "字典编号不允许为空")
    @Length(min = 2, max = 60, message = "字典编号长度要求在{min}-{max}之间")
    private String code;

    @ApiModelProperty(name = "reason", value = "字典备注", required = true, dataType = "String")
    @NotNull(message = "字典备注不允许为空")
    private String reason;

    @TableField(exist = false)
    private List<DictionaryDetail> dictionaryDetailList;
}
