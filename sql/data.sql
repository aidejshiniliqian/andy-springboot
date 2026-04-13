-- RBAC2 权限管理系统初始数据

-- ============================================
-- 1. 初始化部门数据
-- ============================================
INSERT INTO `sys_dept` (`id`, `dept_code`, `dept_name`, `parent_id`, `path`, `sort`, `status`, `description`) VALUES
(1, 'DEPT_001', '总公司', 0, '1', 1, 1, '总公司'),
(2, 'DEPT_002', '技术部', 1, '1,2', 1, 1, '技术研发部门'),
(3, 'DEPT_003', '产品部', 1, '1,3', 2, 1, '产品设计部门'),
(4, 'DEPT_004', '测试部', 2, '1,2,4', 1, 1, '软件测试部门'),
(5, 'DEPT_005', '运维部', 2, '1,2,5', 2, 1, '系统运维部门');

-- ============================================
-- 2. 初始化角色数据
-- ============================================
INSERT INTO `sys_role` (`id`, `role_code`, `role_name`, `description`, `sort`, `status`) VALUES
(1, 'ROLE_ADMIN', '超级管理员', '系统超级管理员，拥有所有权限', 1, 1),
(2, 'ROLE_USER', '普通用户', '普通用户角色', 2, 1),
(3, 'ROLE_DEPT_ADMIN', '部门管理员', '部门管理员角色', 3, 1),
(4, 'ROLE_TEST', '测试人员', '测试人员角色', 4, 1),
(5, 'ROLE_DEV', '开发人员', '开发人员角色', 5, 1);

-- ============================================
-- 3. 初始化权限数据 (菜单权限)
-- ============================================
-- 系统管理模块
INSERT INTO `sys_permission` (`id`, `permission_code`, `permission_name`, `type`, `parent_id`, `path`, `component`, `icon`, `sort`, `status`, `description`) VALUES
(1, 'system:manage', '系统管理', 1, 0, '/system', NULL, 'Setting', 1, 1, '系统管理模块');

-- 用户管理
INSERT INTO `sys_permission` (`id`, `permission_code`, `permission_name`, `type`, `parent_id`, `path`, `component`, `icon`, `sort`, `status`, `description`) VALUES
(2, 'system:user:view', '用户管理', 1, 1, '/system/user', 'system/user/index', 'User', 1, 1, '用户管理菜单'),
(3, 'system:user:list', '用户列表', 3, 2, NULL, NULL, NULL, 1, 1, '查看用户列表'),
(4, 'system:user:add', '新增用户', 2, 2, NULL, NULL, NULL, 2, 1, '新增用户权限'),
(5, 'system:user:edit', '编辑用户', 2, 2, NULL, NULL, NULL, 3, 1, '编辑用户权限'),
(6, 'system:user:delete', '删除用户', 2, 2, NULL, NULL, NULL, 4, 1, '删除用户权限');

-- 角色管理
INSERT INTO `sys_permission` (`id`, `permission_code`, `permission_name`, `type`, `parent_id`, `path`, `component`, `icon`, `sort`, `status`, `description`) VALUES
(7, 'system:role:view', '角色管理', 1, 1, '/system/role', 'system/role/index', 'Role', 2, 1, '角色管理菜单'),
(8, 'system:role:list', '角色列表', 3, 7, NULL, NULL, NULL, 1, 1, '查看角色列表'),
(9, 'system:role:add', '新增角色', 2, 7, NULL, NULL, NULL, 2, 1, '新增角色权限'),
(10, 'system:role:edit', '编辑角色', 2, 7, NULL, NULL, NULL, 3, 1, '编辑角色权限'),
(11, 'system:role:delete', '删除角色', 2, 7, NULL, NULL, NULL, 4, 1, '删除角色权限');

-- 权限管理
INSERT INTO `sys_permission` (`id`, `permission_code`, `permission_name`, `type`, `parent_id`, `path`, `component`, `icon`, `sort`, `status`, `description`) VALUES
(12, 'system:permission:view', '权限管理', 1, 1, '/system/permission', 'system/permission/index', 'Permission', 3, 1, '权限管理菜单'),
(13, 'system:permission:list', '权限列表', 3, 12, NULL, NULL, NULL, 1, 1, '查看权限列表'),
(14, 'system:permission:add', '新增权限', 2, 12, NULL, NULL, NULL, 2, 1, '新增权限'),
(15, 'system:permission:edit', '编辑权限', 2, 12, NULL, NULL, NULL, 3, 1, '编辑权限'),
(16, 'system:permission:delete', '删除权限', 2, 12, NULL, NULL, NULL, 4, 1, '删除权限');

-- 部门管理
INSERT INTO `sys_permission` (`id`, `permission_code`, `permission_name`, `type`, `parent_id`, `path`, `component`, `icon`, `sort`, `status`, `description`) VALUES
(17, 'system:dept:view', '部门管理', 1, 1, '/system/dept', 'system/dept/index', 'Dept', 4, 1, '部门管理菜单'),
(18, 'system:dept:list', '部门列表', 3, 17, NULL, NULL, NULL, 1, 1, '查看部门列表'),
(19, 'system:dept:add', '新增部门', 2, 17, NULL, NULL, NULL, 2, 1, '新增部门权限'),
(20, 'system:dept:edit', '编辑部门', 2, 17, NULL, NULL, NULL, 3, 1, '编辑部门权限'),
(21, 'system:dept:delete', '删除部门', 2, 17, NULL, NULL, NULL, 4, 1, '删除部门权限');

-- ============================================
-- 4. 初始化用户数据
-- 密码: admin123 (BCrypt加密后的密码)
-- ============================================
INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `email`, `phone`, `status`, `remark`) VALUES
(1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '超级管理员', 'admin@example.com', '13800138000', 1, '系统超级管理员'),
(2, 'user', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '普通用户', 'user@example.com', '13800138001', 1, '普通用户'),
(3, 'dev', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '开发人员', 'dev@example.com', '13800138002', 1, '开发人员'),
(4, 'test', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EO', '测试人员', 'test@example.com', '13800138003', 1, '测试人员');

-- ============================================
-- 5. 初始化用户角色关联
-- ============================================
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(2, 2),
(3, 5),
(4, 4);

-- ============================================
-- 6. 初始化角色权限关联 (超级管理员拥有所有权限)
-- ============================================
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES
-- 超级管理员角色拥有所有权限
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
(1, 7), (1, 8), (1, 9), (1, 10), (1, 11),
(1, 12), (1, 13), (1, 14), (1, 15), (1, 16),
(1, 17), (1, 18), (1, 19), (1, 20), (1, 21);

-- 普通用户角色只拥有查看权限
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES
(2, 1), (2, 2), (2, 3), (2, 7), (2, 8), (2, 12), (2, 13), (2, 17), (2, 18);

-- 开发人员角色
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES
(5, 1), (5, 2), (5, 3), (5, 7), (5, 8), (5, 12), (5, 13);

-- 测试人员角色
INSERT INTO `sys_role_permission` (`role_id`, `permission_id`) VALUES
(4, 1), (4, 2), (4, 3), (4, 7), (4, 8);

-- ============================================
-- 7. 初始化用户部门关联
-- ============================================
INSERT INTO `sys_user_dept` (`user_id`, `dept_id`, `is_primary`) VALUES
(1, 1, 1),
(2, 3, 1),
(3, 2, 1),
(4, 4, 1);
