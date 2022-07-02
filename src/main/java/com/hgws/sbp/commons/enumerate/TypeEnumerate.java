package com.hgws.sbp.commons.enumerate;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-30 17:29
 * @description: 系统管理·枚举类型
 */
public enum TypeEnumerate {
    /**
     * 操作类型
     */
    INSERT("insert"),
    UPDATE("update"),
    INSERT_UPDATE("insert_update"),
    DELETE("delete"),
    SELECT("select");
    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    TypeEnumerate(String value) {
        this.value = value;
    }
}
