package com.hgws.sbp.configurations.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.hgws.sbp.configurations.mybatis.interceptor.CustomAutoFillInterceptor;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.aop.interceptor.PerformanceMonitorInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.annotation.Resource;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-06-29 16:56
 * @description: MybatisPlus公共配置信息
 */
@Configuration
@MapperScan(basePackages = "com.hgws.sbp.modules.*.*.dao")
public class MybatisConfiguration {

    @Resource
    private CustomAutoFillInterceptor customAutoFillInterceptor;

    /**
     * 向MybatisPlus中注册插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 注册自动插件
        interceptor.addInnerInterceptor(customAutoFillInterceptor);
        // 注册分页插件
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 注册乐观锁插件
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 防止全表更新删除插件
        interceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        return interceptor;
    }

    /**
     * 指定默认执行器
     * ExecutorType.SIMPLE  简单
     * ExecutorType.BATCH   批量
     * ExecutorType.REUSE   复用
     * ExecutorType.CACHING 缓存
     * @return SIMPLE
     */
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
    }

    /**
     * SQL执行效率插件, 性能分析拦截器，用于输出每条 SQL 语句及其执行时间
     * @return PerformanceMonitorInterceptor
     */
    @Bean
    @Profile({ "dev", "test"})
    public PerformanceMonitorInterceptor performanceInterceptor() {
        return new PerformanceMonitorInterceptor();
    }

}
