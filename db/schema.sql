-- ----------------------------
-- Table structure for system_logger
-- ----------------------------
DROP TABLE IF EXISTS `system_logger`;
CREATE TABLE `system_logger`
(
    `id`             int(11)       NOT NULL AUTO_INCREMENT COMMENT '主键',
    `module`         varchar(60)   NULL DEFAULT NULL COMMENT '模块',
    `type`           varchar(60)   NULL DEFAULT NULL COMMENT '类型',
    `message`        varchar(900)  NULL DEFAULT NULL COMMENT '操作日志',
    `level`          int(11)       NULL DEFAULT NULL COMMENT '日志级别',
    `request_url`    varchar(255)  NULL DEFAULT NULL COMMENT '请求URL',
    `request_method` varchar(60)   NULL DEFAULT NULL COMMENT '请求方式',
    `request_ipaddr` varchar(60)   NULL DEFAULT NULL COMMENT '客户端IP地址',
    `execute_params` varchar(900)  NULL DEFAULT NULL COMMENT '执行参数',
    `execute_time`   int(11)       NULL DEFAULT NULL COMMENT '执行时长',
    `return_value`   varchar(2000) NULL DEFAULT NULL COMMENT '返回结果',
    `revision`       int(11)       NULL DEFAULT NULL COMMENT '乐观锁',
    `delete_flag`    int(11)       NULL DEFAULT NULL COMMENT '删除状态',
    `created_by`     int(11)       NULL DEFAULT NULL COMMENT '创建人',
    `created_time`   datetime      NULL DEFAULT NULL COMMENT '创建时间',
    `updated_by`     int(11)       NULL DEFAULT NULL COMMENT '更新人',
    `updated_time`   datetime      NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '系统管理_日志管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`         varchar(16)  NOT NULL COMMENT '登陆账号',
    `pass`         varchar(60)  NOT NULL COMMENT '登陆密码',
    `gender`       int(11)      NULL DEFAULT NULL COMMENT '用户性别',
    `nick`         varchar(60)  NULL DEFAULT NULL COMMENT '用户昵称',
    `phone`        varchar(11)  NULL DEFAULT NULL COMMENT '手机号码',
    `email`        varchar(30)  NULL DEFAULT NULL COMMENT '电子邮箱',
    `birthday`     date         NULL DEFAULT NULL COMMENT '出生日期',
    `address`      varchar(60)  NULL DEFAULT NULL COMMENT '家庭住址',
    `remarks`      varchar(900) NULL DEFAULT NULL COMMENT '个人介绍',
    `locked`       int(11)      NULL DEFAULT NULL COMMENT '是否锁定',
    `revision`     int(11)      NULL DEFAULT NULL COMMENT '乐观锁',
    `delete_flag`  int(11)      NULL DEFAULT NULL COMMENT '删除状态',
    `created_by`   int(11)      NULL DEFAULT NULL COMMENT '创建人',
    `created_time` datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `updated_by`   int(11)      NULL DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 COMMENT = '系统管理_用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user 系统管理·用户管理
-- ----------------------------
INSERT INTO `system_user` VALUES (1, 'admin', '$2a$10$N5J8rQ3mkl0U5pUbf667R.x0IbMcLAOx4yQiAedf86iYxeoYU12YS', 1, '旺财', '15002931231', '15002931231@136.com', '2022-04-18', '北京嘉华', '这个人很懒...', 0, 1, 0, 1, '2022-04-19 09:49:10', NULL, NULL);
INSERT INTO `system_user` VALUES (2, 'guest', '$2a$10$r/l7/3fuRhohKOa1L8AGzOavcEP.xuxUGDfbbpnqbjm9V.JvL9XJG', 2, '阿福', '19203919210', '19203919210@126.net', '2022-04-09', '天津滨海', '这个人很懒...', 0, 1, 0, 1, '2022-04-19 09:52:26', NULL, NULL);

-- ----------------------------
-- Table structure for system_role
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`         varchar(255) NOT NULL COMMENT '角色名称',
    `code`         varchar(255) NOT NULL COMMENT '角色编号',
    `revision`     int(11)      NULL DEFAULT NULL COMMENT '乐观锁',
    `delete_flag`  int(11)      NULL DEFAULT NULL COMMENT '删除状态',
    `created_by`   int(11)      NULL DEFAULT NULL COMMENT '创建人',
    `created_time` datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `updated_by`   int(11)      NULL DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 COMMENT = '系统管理_角色管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_role 系统管理·角色管理
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '超级管理员', 'ROLE_ADMIN', 1, 0, 1, '2022-09-02 09:36:01', NULL, NULL);
INSERT INTO `system_role` VALUES (2, '普通用户', 'ROLE_GUEST', 1, 0, 1, '2022-09-02 09:36:36', NULL, NULL);

-- ----------------------------
-- Table structure for system_authority
-- ----------------------------
DROP TABLE IF EXISTS `system_authority`;
CREATE TABLE `system_authority`
(
    `id`           int(11)      NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name`         varchar(255) NOT NULL COMMENT '权限名称',
    `code`         varchar(255) NOT NULL COMMENT '权限编码',
    `uri`          varchar(255) NULL DEFAULT NULL COMMENT '访问路径',
    `revision`     int(11)      NULL DEFAULT NULL COMMENT '乐观锁',
    `delete_flag`  int(11)      NULL DEFAULT NULL COMMENT '删除状态',
    `created_by`   int(11)      NULL DEFAULT NULL COMMENT '创建人',
    `created_time` datetime     NULL DEFAULT NULL COMMENT '创建时间',
    `updated_by`   int(11)      NULL DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime     NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 COMMENT = '系统管理_权限管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_authority 系统管理·权限管理
-- ----------------------------
INSERT INTO `system_authority` VALUES (1, '用户添加', 'system:user:save', NULL, 1, 0, 1, '2022-09-02 09:37:23', NULL, NULL);
INSERT INTO `system_authority` VALUES (2, '用户编辑', 'system:user:update', NULL, 1, 0, 1, '2022-09-02 09:37:23', NULL, NULL);
INSERT INTO `system_authority` VALUES (3, '用户删除', 'system:user:delete', NULL, 1, 0, 1, '2022-09-02 09:37:23', NULL, NULL);
INSERT INTO `system_authority` VALUES (4, '用户主键查询', 'system:user:load', NULL, 1, 0, 1, '2022-09-02 09:37:23', NULL, NULL);
INSERT INTO `system_authority` VALUES (5, '用户分页查询', 'system:user:page', NULL, 1, 0, 11, '2022-09-02 09:37:23', NULL, NULL);

-- ----------------------------
-- Table structure for system_user_role
-- ----------------------------
DROP TABLE IF EXISTS `system_user_role`;
CREATE TABLE `system_user_role`
(
    `user_id` int(11) NOT NULL COMMENT '用户外键',
    `role_id` int(11) NOT NULL COMMENT '角色外键',
    PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '系统管理_用户角色关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user_role 系统管理·用户角色关系管理
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 1);
INSERT INTO `system_user_role` VALUES (2, 2);

-- ----------------------------
-- Table structure for system_role_authority
-- ----------------------------
DROP TABLE IF EXISTS `system_role_authority`;
CREATE TABLE `system_role_authority`
(
    `role_id`      int(11) NOT NULL COMMENT '角色外键',
    `authority_id` int(11) NOT NULL COMMENT '权限外键',
    PRIMARY KEY (`role_id`, `authority_id`) USING BTREE
) ENGINE = InnoDB COMMENT = '系统管理_角色权限关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_role_authority 系统管理·角色权限关系管理
-- ----------------------------
INSERT INTO `system_role_authority` VALUES (1, 1);
INSERT INTO `system_role_authority` VALUES (1, 2);
INSERT INTO `system_role_authority` VALUES (1, 3);
INSERT INTO `system_role_authority` VALUES (1, 4);
INSERT INTO `system_role_authority` VALUES (1, 5);
INSERT INTO `system_role_authority` VALUES (2, 3);
INSERT INTO `system_role_authority` VALUES (2, 5);


-- ----------------------------
-- Table structure for system_data_dictionary
-- ----------------------------
DROP TABLE IF EXISTS `system_data_dictionary`;
CREATE TABLE `system_data_dictionary`  (
   `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
   `name` varchar(60) NOT NULL COMMENT '字典名称',
   `code` varchar(60) NULL DEFAULT NULL COMMENT '字典编号',
   `reason` varchar(90) NULL DEFAULT NULL COMMENT '字典备注',
   `revision` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
   `delete_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态',
   `created_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
   `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
   `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新人',
   `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 COMMENT = '系统管理-数据字典' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of system_data_dictionary
-- ----------------------------
INSERT INTO `system_data_dictionary` VALUES (1, '性别', 'gender', '用户性别', 1, 0, 1, '2022-10-27 11:10:44', NULL, NULL);
INSERT INTO `system_data_dictionary` VALUES (2, '状态', 'state', '启禁用状态', 1, 0, 1, '2021-11-02 15:08:25', NULL, NULL);
-- ----------------------------
-- Table structure for system_data_dictionary_detail
-- ----------------------------
DROP TABLE IF EXISTS `system_data_dictionary_detail`;
CREATE TABLE `system_data_dictionary_detail`  (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(60) NULL DEFAULT NULL COMMENT '字典名称',
    `code` varchar(60) NULL DEFAULT NULL COMMENT '字典数值',
    `sorted` int(11) NULL DEFAULT NULL COMMENT '字典排序',
    `reason` varchar(90) NULL DEFAULT NULL COMMENT '字典备注',
    `dictionary_id` int(32) NULL DEFAULT NULL COMMENT '字典外键',
    `revision` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
    `delete_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态',
    `created_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
    `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 COMMENT = '系统管理-数据字典-详情信息' ROW_FORMAT = Dynamic;
-- ----------------------------
-- Records of system_data_dictionary_detail
-- ----------------------------
INSERT INTO `system_data_dictionary_detail` VALUES (1, '男', '1', 1, '性别男', 1, 1, 0, 1, '2022-10-27 11:12:51', NULL, NULL);
INSERT INTO `system_data_dictionary_detail` VALUES (2, '女', '2', 2, '性别女', 1, 1, 0, 1, '2022-10-27 11:13:14', NULL, NULL);
INSERT INTO `system_data_dictionary_detail` VALUES (3, '保密', '3', 3, '性别保密', 1, 1, 0, 1, '2022-10-27 11:13:25', NULL, NULL);
INSERT INTO `system_data_dictionary_detail` VALUES (4, '启用', '0', 1, '可以访问系统', 2, 1, 0, 1, '2021-11-02 17:15:12', NULL, NULL);
INSERT INTO `system_data_dictionary_detail` VALUES (5, '禁用', '1', 2, '禁止访问系统', 2, 1, 0, 1, '2021-11-02 17:15:32', NULL, NULL);