-- ----------------------------
-- Table structure for system_logger
-- ----------------------------
DROP TABLE IF EXISTS `system_logger`;
CREATE TABLE `system_logger`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `module` varchar(60) NULL DEFAULT NULL COMMENT '模块',
  `type` varchar(60) NULL DEFAULT NULL COMMENT '类型',
  `message` varchar(900) NULL DEFAULT NULL COMMENT '操作日志',
  `level` int(11) NULL DEFAULT NULL COMMENT '日志级别',
  `request_url` varchar(255) NULL DEFAULT NULL COMMENT '请求URL',
  `request_method` varchar(60) NULL DEFAULT NULL COMMENT '请求方式',
  `request_ipaddr` varchar(60) NULL DEFAULT NULL COMMENT '客户端IP地址',
  `execute_params` varchar(900) NULL DEFAULT NULL COMMENT '执行参数',
  `execute_time` int(11) NULL DEFAULT NULL COMMENT '执行时长',
  `return_value` varchar(2000) NULL DEFAULT NULL COMMENT '返回结果',
  `revision` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `delete_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态',
  `created_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
  `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新人',
  `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 COMMENT = '系统管理_日志管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user`  (
    `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
    `name` varchar(16) NOT NULL COMMENT '登陆账号',
    `pass` varchar(60) NOT NULL COMMENT '登陆密码',
    `gender` int(11) NULL DEFAULT NULL COMMENT '用户性别',
    `nick` varchar(60) NULL DEFAULT NULL COMMENT '用户昵称',
    `phone` varchar(11) NULL DEFAULT NULL COMMENT '手机号码',
    `email` varchar(30) NULL DEFAULT NULL COMMENT '电子邮箱',
    `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
    `address` varchar(60) NULL DEFAULT NULL COMMENT '家庭住址',
    `remarks` varchar(900) NULL DEFAULT NULL COMMENT '个人介绍',
    `locked` int(11) NULL DEFAULT NULL COMMENT '是否锁定',
    `revision` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
    `delete_flag` int(11) NULL DEFAULT NULL COMMENT '删除状态',
    `created_by` int(11) NULL DEFAULT NULL COMMENT '创建人',
    `created_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
    `updated_by` int(11) NULL DEFAULT NULL COMMENT '更新人',
    `updated_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 COMMENT = '系统管理_用户管理' ROW_FORMAT = Dynamic;
