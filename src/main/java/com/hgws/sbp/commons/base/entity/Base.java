package com.hgws.sbp.commons.base.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-29 17:29
 * @description: 公共基础属性
 */
@Getter
@Setter
@ToString
public class Base {
    /**
     * 主键ID数据库自增
     */
    @TableId(type= IdType.AUTO)
    private Integer id;
    /**
     * 乐观锁(在并发操作时使用)
     * 1.select revision from table where id = ?
     * 2.判断用户传入的revision与数据库查询revision是否相等
     * 3.1.相等则执行修改 revision = revision + 1
     * 3.2.不相等则不执行数据库 update
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer revision;
    /**
     * 删除状态0:启用1:删除
     * 逻辑删除 0 1
     */
    @TableLogic
    private Integer deleteFlag = 0;
    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer createdBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer updatedBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;
    /**
     * pageNo:      起始页
     * 不参与数据库相关操作只负责分页查询相关功能
     */
    @TableField(exist = false)
    private int pageNo = 1;
    /**
     * pageSize:    每页查询条数
     * 不参与数据库相关操作只负责分页查询相关功能
     */
    @TableField(exist = false)
    private int pageSize = 10;
}
