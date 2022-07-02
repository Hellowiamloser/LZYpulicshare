package com.hgws.sbp.commons.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-07-02 15:21
 * @description: TODO
 */
@Component
public class BeanUtils implements BeanFactoryPostProcessor {

    //IoC容器-BeanFactory
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(@NotNull ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        BeanUtils.beanFactory = beanFactory;
    }

    /**
     * 获取IoC容器管理实例
     * @param name bean唯一标示符
     * @return Object 根据ID返回实例
     */
    public static Object getBeanName(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactory.getBean(name);
    }

    /**
     * 获取IoC容器管理实例
     * @param classes IoC容器管理实例
     * @return Object 根据类型返回实例
     */
    public static <T> T getBeanType(Class<T> classes) throws NoSuchBeanDefinitionException
    {
        return beanFactory.getBean(classes);
    }

}
