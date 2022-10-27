package com.hgws.sbp.commons.utils;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.cglib.beans.BeanGenerator;
import org.springframework.cglib.beans.BeanMap;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-10-27
 * @description: 动态属性增强
 */
public class PropertiesUtils {

    /**
     * 向原生对象添加动态属性
     * @param dest  原生对象
     * @param addProperties 属性集合
     * @return 全新对象
     */
    public static Object getTarget(Object dest, Map<String, Object> addProperties) {
        PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean();
        PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(dest);
        Map<String, Class> propertyMap = new HashMap<>();
        for (PropertyDescriptor d : descriptors) {
            if (!"class".equalsIgnoreCase(d.getName())) {
                propertyMap.put(d.getName(), d.getPropertyType());
            }
        }
        // add extra properties
        addProperties.forEach((k, v) -> propertyMap.put(k, v.getClass()));
        // new dynamic bean
        DynamicBean dynamicBean = new DynamicBean(dest.getClass(), propertyMap);
        // add old value
        propertyMap.forEach((k, v) -> {
            try {
                // filter extra properties
                if (!addProperties.containsKey(k)) {
                    dynamicBean.setValue(k, propertyUtilsBean.getNestedProperty(dest, k));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        // add extra value
        addProperties.forEach((k, v) -> {
            try {
                dynamicBean.setValue(k, v);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        Object target = dynamicBean.getTarget();
        return target;
    }

    static class DynamicBean {
        /**
         * 目标对象
         */
        private Object target;
        /**
         * 属性集合
         */
        private BeanMap beanMap;


        public DynamicBean(Class superclass, Map<String, Class> propertyMap) {
            this.target = generateBean(superclass, propertyMap);
            this.beanMap = BeanMap.create(this.target);
        }

        /**
         * bean 添加属性和值
         * @param property
         * @param value
         */
        public void setValue(String property, Object value) {
            beanMap.put(property, value);
        }
        /**
         * 获取属性值
         * @param property
         * @return
         */
        public Object getValue(String property) {
            return beanMap.get(property);
        }
        /**
         * 获取对象
         * @return
         */
        public Object getTarget() {
            return this.target;
        }
        /**
         * 根据属性生成对象
         * @param superclass
         * @param propertyMap
         * @return
         */
        private Object generateBean(Class superclass, Map<String, Class> propertyMap) {
            BeanGenerator generator = new BeanGenerator();
            if (null != superclass) {
                generator.setSuperclass(superclass);
            }
            BeanGenerator.addProperties(generator, propertyMap);
            return generator.create();
        }
    }
}
