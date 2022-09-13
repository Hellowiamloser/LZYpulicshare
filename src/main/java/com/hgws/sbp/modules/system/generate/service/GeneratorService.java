package com.hgws.sbp.modules.system.generate.service;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.hgws.sbp.commons.base.dao.BaseDao;
import com.hgws.sbp.commons.base.entity.Base;
import com.hgws.sbp.commons.base.service.BaseService;
import com.hgws.sbp.components.properties.DataSourceProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Collections;
import java.util.Map;

/**
 * @author zhouhonggang
 * @version 1.0.0
 * @project spring-boot-pro
 * @datetime 2022-04-13 13:53
 * @description: 一键生成模块
 */
@Service
public class GeneratorService {

    /**
     * 当前数据库
     */
    private String schema;
    /**
     * 当前表名称
     */
    private String TABLE_NAME = "";
    /**
     * 上级模块名称
     */
    private String PARENT_MODULE = "";
    /**
     * 当前模块名称
     */
    private String CURRENT_MODULE = "";
    /**
     * 数据库连接
     */
    private DataSourceProperties dataSourceProperties;
    /**
     * 忽略表
     */
    private final static String[] EXCLUDE_TABLE_PREFIX = {"test_"};
    /**
     * 项目路径
     */
    private final static String PROJECT_PATH = System.getProperty("user.dir");
    /**
     * 源码目录
     */
    private final static String OUTPUT_SRC_PATH = PROJECT_PATH + "/src/main/java/";
    /**
     * 配置目录
     */
    private final static String OUTPUT_MAPPER_PATH = PROJECT_PATH + "/src/main/resources/mapper/";

    @Autowired
    public GeneratorService(DataSourceProperties dataSourceProperties) {
        this.dataSourceProperties = dataSourceProperties;
        String url = dataSourceProperties.getUrl();
        String url1 = url.substring(0, url.indexOf("?"));
        schema = url1.substring(url1.lastIndexOf("/")+1);
    }

    public void generator(Map<String, String> params)
    {
        TABLE_NAME = params.get("name");
        PARENT_MODULE = params.get("parent");
        CURRENT_MODULE = params.get("current");

        FastAutoGenerator.create(dataSourceProperties.getUrl(), dataSourceProperties.getUsername(), dataSourceProperties.getPassword())
                //全局信息配置
                .globalConfig(builder -> {
                    builder
                            .enableSwagger()                // 启用swagger
                            .disableOpenDir()               // 不打开文件夹
                            .author("zhouhonggang")         // 设置作者信息
                            //.fileOverride()               // 覆盖已生成文件
                            .outputDir(OUTPUT_SRC_PATH);    // 指定输出目录
                })
                //包相关配置
                .packageConfig(builder -> {
                    builder.parent("com.hgws.sbp.modules."+PARENT_MODULE)    // 设置父包
                            .moduleName(CURRENT_MODULE)                      // 设置父包模块名
                            .controller("controller")
                            .serviceImpl("service")
                            .mapper("dao")
                            .entity("entity")
                            .pathInfo(Collections.singletonMap(OutputFile.xml, OUTPUT_MAPPER_PATH + PARENT_MODULE)); // 设置mapperXml生成路径
                })
                //模块模板配置
                .templateConfig(builder -> {
                    builder.service(null);
                    builder.controller("templates/generator/controller.java");
                    builder.serviceImpl("templates/generator/serviceImpl.java");
                    builder.mapper("templates/generator/mapper.java");
                    builder.entity("templates/generator/entity.java");
                    builder.xml("templates/generator/mapper.xml");
                })
                //策略配置
                .strategyConfig(builder -> {
                    builder.addInclude(TABLE_NAME)                  // 设置需要生成的表名
                            .addTablePrefix(EXCLUDE_TABLE_PREFIX);  // 设置过滤表前缀
                    //表现层配置
                    builder.controllerBuilder()
                            .enableRestStyle();
                    //逻辑层配置
                    builder.serviceBuilder()
                            .superServiceImplClass(BaseService.class)
                            .formatServiceImplFileName("%sService");
                    //数据层配置
                    builder.mapperBuilder()
                            .formatMapperFileName("%sDao")
                            .superClass(BaseDao.class)
                            .enableBaseColumnList()
                            .enableBaseResultMap();
                    //实体类配置
                    builder.entityBuilder()
                            .superClass(Base.class)         //继承父类
                            .enableLombok()                 //启用lombok
                            .disableSerialVersionUID()      //禁用序列化
                            .addSuperEntityColumns("id", "revision", "delete_flag", "created_by", "created_time", "updated_by", "updated_time");    //忽略字段
                })
                // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }

}
