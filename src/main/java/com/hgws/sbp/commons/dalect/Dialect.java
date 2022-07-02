package com.hgws.sbp.commons.dalect;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-29 16:57
 * @description: 针对不同数据库Dialect
 */
public interface Dialect {

    /**
     * 数据库本身是否支持分页当前的分页查询方式
     * 如果数据库不支持的话，则不进行数据库分页
     * @return true: 支持当前的分页查询方式
     */
    boolean supportsLimit();

    /**
     * 将sql转换为分页SQL，分别调用分页sql
     * @param sql       sql语句
     * @param offset    起始条数
     * @param limit     每页条数
     * @return          分页查询的sql
     */
    String getLimitString(String sql, int offset, int limit);

}
