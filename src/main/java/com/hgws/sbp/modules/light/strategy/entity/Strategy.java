package com.hgws.sbp.modules.light.strategy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hgws.sbp.commons.base.entity.Base;
import com.hgws.sbp.modules.light.area.entity.LightArea;
import com.hgws.sbp.modules.light.group.entity.Group;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@ApiModel("策略属性")
@TableName("strategy")
public class Strategy extends Base {

    /** 策略编号 */
    @ApiModelProperty(name = "strategyId",value = "策略编号",required = true)
    private String strategyId ;

    /** 策略名称 */
    @ApiModelProperty(name = "strategyName",value = "策略名称",required = true)
    private String strategyName ;

    /** 启用状态 */
    @ApiModelProperty(name = "upState",value = "启用状态",required = true)
    private Integer upState ;

    /** 分组编号(外键) */
    @ApiModelProperty(name = "strategyGroup",value = "分组编号",required = true)
    @TableField(exist = false)
    private Group strategyGroup ;

    /** 区域编号(外键) */
    @ApiModelProperty(name = "strategyArea",value = "区域编号",required = true)
    @TableField(exist = false)
    private LightArea strategyArea ;

    /** 开灯计划(外键) */
    @ApiModelProperty(name = "strategyScheduled",value = "开灯计划",required = true)
    @TableField(exist = false)
    private Scheduled strategyScheduled ;

    @TableField(exist = false)
    @ApiModelProperty(name = "turnLightOnTime",value = "开灯时间",required = true)
    private String turnLightOnTime;

    @TableField(exist = false)
    @ApiModelProperty(name = "turnOnTheLightCycle",value = "开灯周期",required = true)
    private String turnOnTheLightCycle;

}
