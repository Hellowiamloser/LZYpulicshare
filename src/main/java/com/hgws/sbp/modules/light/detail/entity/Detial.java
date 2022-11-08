package com.hgws.sbp.modules.light.detail.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hgws.sbp.commons.base.entity.Base;
import com.hgws.sbp.modules.light.area.entity.LightArea;
import com.hgws.sbp.modules.light.group.entity.Group;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@TableName("light_details")
public class Detial extends Base {
    /** 灯具编号 */
    private String lightCode ;
    /** 灯具名称 */
    private String lightName ;
    /** 灯具类型 */
    private String lightType ;
    /** 灯具品牌数据字典 */
    private Integer lightBrand ;
    /** 安装时间 */
    private Date installTime ;
    /** 灯具电流 */
    private Integer lightCurrent ;
    /** 灯具电压 */
    private Integer lightVoltage ;
    /** 在线状态数据字典 */
    private Integer onlineState ;
    /** 开关状态数据字典 */
    private Integer switchState ;
    /** 区域经度 */
    private BigDecimal areaLng ;
    /** 区域纬度 */
    private BigDecimal areaLat ;
    /** 区域位置 */
    private String areaAddress ;
    /** 灯具备注 */
    private String lightNote ;

    //    /** 区域外键 */外键处理
    //    private Integer lightAreaId ;
    @TableField(exist = false)
    private LightArea lightArea;//多对一外键处理,只能属于一个区域

    @TableField(exist = false)
    private List<Group> lightGroup;//多对多处理,可以所属多个分组

}
