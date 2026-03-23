-- =============================================
-- RBAC2 权限模型数据库设计
-- =============================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS andy_db DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE andy_db;

-- ---------------------------------------------
-- 1. 用户表
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    username VARCHAR(50) NOT NULL COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '密码',
    nickname VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    email VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    phone VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    gender TINYINT DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    avatar VARCHAR(255) DEFAULT NULL COMMENT '头像',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- ---------------------------------------------
-- 2. 角色表
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    role_name VARCHAR(50) NOT NULL COMMENT '角色名称',
    role_code VARCHAR(50) NOT NULL COMMENT '角色编码',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_code (role_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- ---------------------------------------------
-- 3. 权限表
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_permission;
CREATE TABLE sys_permission (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限ID',
    permission_name VARCHAR(50) NOT NULL COMMENT '权限名称',
    permission_code VARCHAR(100) NOT NULL COMMENT '权限编码',
    permission_type TINYINT NOT NULL COMMENT '权限类型：1-菜单，2-按钮，3-接口',
    parent_id BIGINT DEFAULT 0 COMMENT '父级ID',
    path VARCHAR(255) DEFAULT NULL COMMENT '路由路径',
    component VARCHAR(255) DEFAULT NULL COMMENT '组件路径',
    icon VARCHAR(100) DEFAULT NULL COMMENT '图标',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_permission_code (permission_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='权限表';

-- ---------------------------------------------
-- 4. 用户角色关联表
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id),
    KEY idx_user_id (user_id),
    KEY idx_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- ---------------------------------------------
-- 5. 角色权限关联表
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_role_permission;
CREATE TABLE sys_role_permission (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    permission_id BIGINT NOT NULL COMMENT '权限ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    PRIMARY KEY (id),
    KEY idx_role_id (role_id),
    KEY idx_permission_id (permission_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色权限关联表';

-- ---------------------------------------------
-- 6. 角色互斥表（RBAC2特性）
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_role_exclusion;
CREATE TABLE sys_role_exclusion (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    exclude_role_id BIGINT NOT NULL COMMENT '互斥角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_role_id (role_id),
    KEY idx_exclude_role_id (exclude_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色互斥表';

-- ---------------------------------------------
-- 7. 角色基数限制表（RBAC2特性）
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_role_cardinality;
CREATE TABLE sys_role_cardinality (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    role_id BIGINT NOT NULL COMMENT '角色ID',
    max_count INT NOT NULL DEFAULT 0 COMMENT '最大用户数量，0表示不限制',
    current_count INT DEFAULT 0 COMMENT '当前用户数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_role_id (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色基数限制表';

-- ---------------------------------------------
-- 8. 角色继承表（RBAC2特性 - 先决条件角色）
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_role_hierarchy;
CREATE TABLE sys_role_hierarchy (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    parent_role_id BIGINT NOT NULL COMMENT '父角色ID（先决条件角色）',
    child_role_id BIGINT NOT NULL COMMENT '子角色ID',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    KEY idx_parent_role_id (parent_role_id),
    KEY idx_child_role_id (child_role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色继承表';

-- ---------------------------------------------
-- 9. 会话表（RBAC2特性）
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_session;
CREATE TABLE sys_session (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    session_id VARCHAR(100) NOT NULL COMMENT '会话ID',
    token VARCHAR(500) DEFAULT NULL COMMENT '令牌',
    login_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
    logout_time DATETIME DEFAULT NULL COMMENT '登出时间',
    login_ip VARCHAR(50) DEFAULT NULL COMMENT '登录IP',
    status TINYINT DEFAULT 1 COMMENT '状态：0-已过期，1-有效',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    UNIQUE KEY uk_session_id (session_id),
    KEY idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='会话表';

-- ---------------------------------------------
-- 10. 操作日志表
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_oper_log;
CREATE TABLE sys_oper_log (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    title VARCHAR(50) DEFAULT NULL COMMENT '操作标题',
    business_type TINYINT DEFAULT 0 COMMENT '业务类型：0-其他，1-新增，2-修改，3-删除，4-查询',
    method VARCHAR(200) DEFAULT NULL COMMENT '方法名称',
    request_method VARCHAR(10) DEFAULT NULL COMMENT '请求方式',
    oper_url VARCHAR(500) DEFAULT NULL COMMENT '请求URL',
    oper_ip VARCHAR(50) DEFAULT NULL COMMENT '操作IP',
    oper_name VARCHAR(50) DEFAULT NULL COMMENT '操作人',
    oper_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
    cost_time BIGINT DEFAULT 0 COMMENT '耗时（毫秒）',
    request_param TEXT COMMENT '请求参数',
    response_result TEXT COMMENT '响应结果',
    status TINYINT DEFAULT 1 COMMENT '状态：0-失败，1-成功',
    error_msg TEXT COMMENT '错误信息',
    PRIMARY KEY (id),
    KEY idx_oper_time (oper_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='操作日志表';

-- ---------------------------------------------
-- 初始化数据
-- ---------------------------------------------

-- 初始化管理员用户（密码：admin123，使用BCrypt加密）
INSERT INTO sys_user (username, password, nickname, email, phone, gender, status, remark) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iAt6Z5EH', '超级管理员', 'admin@example.com', '13800138000', 1, 1, '超级管理员账号');

-- 初始化角色
INSERT INTO sys_role (role_name, role_code, sort, status, remark) VALUES
('超级管理员', 'SUPER_ADMIN', 1, 1, '拥有所有权限'),
('管理员', 'ADMIN', 2, 1, '普通管理员'),
('普通用户', 'USER', 3, 1, '普通用户');

-- 初始化权限（菜单）
INSERT INTO sys_permission (permission_name, permission_code, permission_type, parent_id, path, component, icon, sort, status) VALUES
('系统管理', 'system', 1, 0, '/system', 'Layout', 'setting', 1, 1),
('用户管理', 'system:user', 1, 1, '/system/user', 'system/user/index', 'user', 1, 1),
('角色管理', 'system:role', 1, 1, '/system/role', 'system/role/index', 'peoples', 2, 1),
('权限管理', 'system:permission', 1, 1, '/system/permission', 'system/permission/index', 'lock', 3, 1);

-- 初始化权限（按钮）
INSERT INTO sys_permission (permission_name, permission_code, permission_type, parent_id, status) VALUES
('用户查询', 'system:user:query', 2, 3, 1),
('用户新增', 'system:user:add', 2, 3, 1),
('用户修改', 'system:user:edit', 2, 3, 1),
('用户删除', 'system:user:delete', 2, 3, 1),
('角色查询', 'system:role:query', 2, 4, 1),
('角色新增', 'system:role:add', 2, 4, 1),
('角色修改', 'system:role:edit', 2, 4, 1),
('角色删除', 'system:role:delete', 2, 4, 1);

-- 初始化用户角色关联
INSERT INTO sys_user_role (user_id, role_id) VALUES
(1, 1);

-- 初始化角色权限关联（超级管理员拥有所有权限）
INSERT INTO sys_role_permission (role_id, permission_id)
SELECT 1, id FROM sys_permission;

-- 初始化角色基数限制
INSERT INTO sys_role_cardinality (role_id, max_count, remark) VALUES
(1, 1, '超级管理员只能有1个'),
(2, 10, '管理员最多10个'),
(3, 0, '普通用户不限制');

-- 初始化角色互斥（示例：管理员和审计员不能同时拥有）
INSERT INTO sys_role (role_name, role_code, sort, status, remark) VALUES
('审计员', 'AUDITOR', 4, 1, '审计人员');

INSERT INTO sys_role_exclusion (role_id, exclude_role_id, remark) VALUES
(2, 5, '管理员和审计员角色互斥'),
(5, 2, '审计员和管理员角色互斥');
