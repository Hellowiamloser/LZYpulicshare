package com.hgws.sbp.modules.light.group.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.hgws.sbp.commons.base.entity.Base;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@TableName(value = "group_manager")
@ApiModel("分组属性")
public class Group extends Base {
    /** 序号 */
    @NotNull(message = "序号不能为空")
    @ApiModelProperty(name = "groupOrder",value = "序号",required = true)
    private Integer groupOrder ;

    /** 分组编号 */
    @NotBlank(message = "分组编号不能为空")
    @ApiModelProperty(name = "groupId",value = "分组编号",required = true)
    private String groupCode ;

    /** 分组名称 */
    @NotBlank(message = "分组名称不能为空")
    @ApiModelProperty(name = "groupName",value = "分组名称",required = true)
    private String groupName ;

    /** 分组备注 */
    @Length(max = 255,message = "最大长度不能超过{max}个字符")
    @ApiModelProperty(name = "groupRemark",value = "分组备注")
    private String groupRemark ;
}
