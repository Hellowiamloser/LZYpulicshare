package com.hgws.sbp.modules.light.area.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hgws.sbp.commons.annotation.DictionaryOperation;
import com.hgws.sbp.commons.base.entity.Base;
import com.hgws.sbp.commons.validator.group.Insert;
import com.hgws.sbp.commons.validator.group.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@TableName(value = "light_area")
@ApiModel("区域属性")
public class LightArea extends Base {//Base中有基本的缺省字段信息所以继承它
    /** 区域编号 */
    @NotBlank(message = "区域编号不能为空",groups ={Update.class, Insert.class})
    @Length(min=3,max = 20,message = "区域编号长度在{min}-{max}之间")
    @ApiModelProperty(name = "areaCode",value = "区域编号",required = true)
    private String areaCode;

    /** 区域名称 */
    @NotBlank(message = "区域名称不能为空",groups ={Update.class, Insert.class})
    @Length(min=3,max = 50,message = "区域编号长度在{min}-{max}之间")
    @ApiModelProperty(name = "areaName",value = "区域名称",required = true)
    private String areaName;

    /** 区域级别 */
    @DictionaryOperation(code = "area_level")
    @NotNull(message = "区域职级不能为空")
    @ApiModelProperty(name = "areaLevel",value = "区域级别",required = true)
    private Integer areaLevel;

    /** 区域外键;数据字典 */
    @NotNull(message = "上级区域不能为空")
    @ApiModelProperty(name = "areaPid",value = "区域外键",required = true)
    private Integer areaPid;

    /** 区域备注 */
    @Length(max = 255,message = "超出最大长度{max}")
    @ApiModelProperty(name = "areaNote",value = "区域备注")
    private String areaNote;
}