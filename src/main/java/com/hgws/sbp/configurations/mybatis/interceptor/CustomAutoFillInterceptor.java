package com.hgws.sbp.configurations.mybatis.interceptor;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.hgws.sbp.commons.utils.UserUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-09-29
 * @description: 自动填充添加人与添加时间
 */
@Component
public class CustomAutoFillInterceptor implements InnerInterceptor {

    @Resource
    private UserUtils userUtils;

    @Override
    public void beforeUpdate(Executor executor, MappedStatement ms, Object parameter) throws SQLException {
        Field[] declaredFields = parameter.getClass().getSuperclass().getDeclaredFields();
        Arrays.stream(declaredFields).forEach(field -> {
            SqlCommandType sqlCommandType = ms.getSqlCommandType();
            TableField tableField = field.getAnnotation(TableField.class);
            if(!ObjectUtils.isEmpty(tableField))
            {
                String fieldName = field.getName();
                field.setAccessible(true);
                if(SqlCommandType.INSERT.equals(sqlCommandType) && tableField.fill().equals(FieldFill.INSERT))
                {
                    try {
                        if("revision".equals(fieldName)) {
                            field.set(parameter, 1);
                        }
                        else if("deleteFlag".equals(fieldName)) {
                            field.set(parameter, 0);
                        }
                        else if("createdBy".equals(fieldName)) {
                            field.set(parameter, userUtils.getUserId());
                        }
                        else if("createdTime".equals(fieldName)) {
                            field.set(parameter, LocalDateTime.now());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                else if(SqlCommandType.UPDATE.equals(sqlCommandType) && tableField.fill().equals(FieldFill.UPDATE))
                {
                    try {
                        if("updatedBy".equals(fieldName)) {
                            field.set(parameter, userUtils.getUserId());
                        }
                        else if("updatedTime".equals(fieldName)) {
                            field.set(parameter, LocalDateTime.now());
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        InnerInterceptor.super.beforeUpdate(executor, ms, parameter);
    }

}
