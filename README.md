# spring-boot-pro

## 介绍

> spring boot pro为前后端分离开发平台, 平台采用spring-boot作为平台搭建基础架构, 数据层采用mybatis-plus,数据源使用阿里巴巴druid, 认证采用spring-security.

## 软件架构
> 前后端分离已经成为互联网项目开发标准，它会为以后的大型分布式架构打下基础。SpringBoot使编码配置部署都变得简单，越来越多的互联网公司已经选择SpringBoot作为微服务的入门级微框架。


## 安装教程

1. 检出spring-boot-pro项目后首先下载相关依赖
2. 导入项目db目录中SQL到数据库(schema.sql)
3. 修改resources/application-dev.yml
   1. 修改文件上传位置信息 [server.path]
   2. 修改数据库连接配置 [spring.datasource]
4. 启动服务器后使用postman完成测试
   1. 首先调用登陆接口获取[ /login ]token
   2. 账号admin 密码123456	->	system_user表
5. 把token写入到Authorization中

## 使用说明

1.  接口地址: http://127.0.0.1:9527/api
2.  文档地址: http://127.0.0.1:9527/swagger-ui/
3.  监控地址: http://127.0.0.1:9527/druid/
    账号: spring 密码: boot