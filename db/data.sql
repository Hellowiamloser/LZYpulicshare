-- ----------------------------
-- Table structure for system_user 系统管理·用户管理
-- ----------------------------
INSERT INTO `system_user` VALUES (1, 'admin', '$2a$10$N5J8rQ3mkl0U5pUbf667R.x0IbMcLAOx4yQiAedf86iYxeoYU12YS', 1, '旺财', '15002931231', '15002931231@136.com', '2022-04-18', '北京嘉华', '这个人很懒...', 0, 1, 0, 1, '2022-04-19 09:49:10', NULL, NULL);
INSERT INTO `system_user` VALUES (2, 'guest', '$2a$10$r/l7/3fuRhohKOa1L8AGzOavcEP.xuxUGDfbbpnqbjm9V.JvL9XJG', 2, '阿福', '19203919210', '19203919210@126.net', '2022-04-09', '天津滨海', '这个人很懒...', 0, 1, 0, 1, '2022-04-19 09:52:26', NULL, NULL);

-- ----------------------------
-- Table structure for system_role 系统管理·角色管理
-- ----------------------------
INSERT INTO `system_role` VALUES (1, '超级管理员', 'ROLE_ADMIN', 1, 0, 1, '2022-09-02 09:36:01', NULL, NULL);
INSERT INTO `system_role` VALUES (2, '普通用户', 'ROLE_GUEST', 1, 0, 1, '2022-09-02 09:36:36', NULL, NULL);

-- ----------------------------
-- Table structure for system_authority 系统管理·权限管理
-- ----------------------------
INSERT INTO `system_authority` VALUES (1, '用户添加', 'system:user:save', NULL, 1, 0, 1, '2022-09-02 09:37:23', NULL, NULL);
INSERT INTO `system_authority` VALUES (2, '用户编辑', 'system:user:update', NULL, 1, 0, 1, '2022-09-02 09:37:23', NULL, NULL);
INSERT INTO `system_authority` VALUES (3, '用户删除', 'system:user:delete', NULL, 1, 0, 1, '2022-09-02 09:37:23', NULL, NULL);
INSERT INTO `system_authority` VALUES (4, '用户主键查询', 'system:user:load', NULL, 1, 0, 1, '2022-09-02 09:37:23', NULL, NULL);
INSERT INTO `system_authority` VALUES (5, '用户分页查询', 'system:user:page', NULL, 1, 0, 11, '2022-09-02 09:37:23', NULL, NULL);

-- ----------------------------
-- Table structure for system_user_role 系统管理·用户角色关系管理
-- ----------------------------
INSERT INTO `system_user_role` VALUES (1, 1);
INSERT INTO `system_user_role` VALUES (2, 2);

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