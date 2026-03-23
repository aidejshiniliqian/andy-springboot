DROP TABLE IF EXISTS `sys_system`;
CREATE TABLE `sys_system` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '子系统ID',
  `system_code` varchar(50) NOT NULL COMMENT '子系统编码',
  `system_name` varchar(100) NOT NULL COMMENT '子系统名称',
  `description` varchar(200) DEFAULT NULL COMMENT '子系统描述',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` tinyint DEFAULT '1' COMMENT '状态：0禁用，1正常',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_system_code` (`system_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='子系统表';

DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) NOT NULL COMMENT '密码',
  `nickname` varchar(50) DEFAULT NULL COMMENT '昵称',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '手机号',
  `avatar` varchar(200) DEFAULT NULL COMMENT '头像',
  `status` tinyint DEFAULT '1' COMMENT '状态：0禁用，1正常',
  `gender` tinyint DEFAULT '2' COMMENT '性别：0女，1男，2未知',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_username` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(50) NOT NULL COMMENT '角色名称',
  `role_key` varchar(50) NOT NULL COMMENT '角色标识',
  `sort` int DEFAULT '0' COMMENT '角色排序',
  `status` tinyint DEFAULT '1' COMMENT '状态：0禁用，1正常',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_role_key` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色表';

DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `path` varchar(200) DEFAULT NULL COMMENT '路由地址',
  `component` varchar(200) DEFAULT NULL COMMENT '组件路径',
  `perms` varchar(100) DEFAULT NULL COMMENT '菜单标识（权限标识）',
  `menu_type` char(1) DEFAULT NULL COMMENT '菜单类型：M目录，C菜单，F按钮',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `sort` int DEFAULT '0' COMMENT '排序',
  `status` tinyint DEFAULT '1' COMMENT '状态：0禁用，1正常',
  `system_code` varchar(50) DEFAULT NULL COMMENT '子系统编码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(50) DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单权限表';

DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户角色关联表';

DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='角色菜单关联表';

INSERT INTO `sys_system` VALUES (1, 'PC', 'PC端管理系统', 'PC端后台管理系统', 1, 1, NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_system` VALUES (2, 'APP', 'APP端应用', '移动端APP应用', 2, 1, NOW(), NOW(), 'system', 'system');

INSERT INTO `sys_user` VALUES (1, 'admin', '$2a$10$7JB720yubgJkq97WCTbW9uuUYQb5q7r5q7r5q7r5q7r5q7r5q', '管理员', 'admin@example.com', '13800000000', NULL, 1, 1, NULL, NOW(), NOW(), 'system', 'system');

INSERT INTO `sys_role` VALUES (1, '超级管理员', 'admin', 1, 1, '超级管理员角色', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_role` VALUES (2, '普通用户', 'user', 2, 1, '普通用户角色', NOW(), NOW(), 'system', 'system');

INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, '/system', NULL, NULL, 'M', 'system', 1, 1, 'PC', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, '/system/user', 'system/user/index', 'system:user:list', 'C', 'user', 1, 1, 'PC', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (3, '角色管理', 1, '/system/role', 'system/role/index', 'system:role:list', 'C', 'peoples', 2, 1, 'PC', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (4, '菜单管理', 1, '/system/menu', 'system/menu/index', 'system:menu:list', 'C', 'tree-table', 3, 1, 'PC', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (5, '新增用户', 2, NULL, NULL, 'system:user:add', 'F', '#', 1, 1, 'PC', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (6, '修改用户', 2, NULL, NULL, 'system:user:edit', 'F', '#', 2, 1, 'PC', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (7, '删除用户', 2, NULL, NULL, 'system:user:delete', 'F', '#', 3, 1, 'PC', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (8, 'APP首页', 0, '/app/home', NULL, NULL, 'M', 'home', 1, 1, 'APP', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (9, '个人中心', 8, '/app/profile', 'app/profile/index', 'app:profile:view', 'C', 'user', 1, 1, 'APP', NOW(), NOW(), 'system', 'system');
INSERT INTO `sys_menu` VALUES (10, '消息中心', 8, '/app/message', 'app/message/index', 'app:message:list', 'C', 'message', 2, 1, 'APP', NOW(), NOW(), 'system', 'system');

INSERT INTO `sys_user_role` VALUES (1, 1);

INSERT INTO `sys_role_menu` VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6), (1, 7), (1, 8), (1, 9), (1, 10);
