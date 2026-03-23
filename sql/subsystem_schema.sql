-- =============================================
-- 多系统菜单支持 - 数据库变更脚本
-- =============================================

-- ---------------------------------------------
-- 1. 创建子系统表
-- ---------------------------------------------
DROP TABLE IF EXISTS sys_subsystem;
CREATE TABLE sys_subsystem (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '子系统ID',
    subsystem_name VARCHAR(50) NOT NULL COMMENT '子系统名称',
    subsystem_code VARCHAR(50) NOT NULL COMMENT '子系统编码',
    description VARCHAR(500) DEFAULT NULL COMMENT '描述',
    sort INT DEFAULT 0 COMMENT '排序',
    status TINYINT DEFAULT 1 COMMENT '状态：0-禁用，1-启用',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    create_by VARCHAR(50) DEFAULT NULL COMMENT '创建人',
    update_by VARCHAR(50) DEFAULT NULL COMMENT '更新人',
    deleted TINYINT DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除',
    remark VARCHAR(500) DEFAULT NULL COMMENT '备注',
    PRIMARY KEY (id),
    UNIQUE KEY uk_subsystem_code (subsystem_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子系统表';

-- ---------------------------------------------
-- 2. 权限表添加子系统编码字段
-- ---------------------------------------------
ALTER TABLE sys_permission ADD COLUMN subsystem_code VARCHAR(50) DEFAULT NULL COMMENT '子系统编码' AFTER status;

-- 添加索引
ALTER TABLE sys_permission ADD INDEX idx_subsystem_code (subsystem_code);

-- ---------------------------------------------
-- 3. 初始化子系统数据
-- ---------------------------------------------
INSERT INTO sys_subsystem (subsystem_name, subsystem_code, description, sort, status, remark) VALUES
('PC端', 'PC', 'PC端管理系统', 1, 1, 'PC端后台管理系统'),
('APP端', 'APP', '移动端APP', 2, 1, '移动端APP应用');

-- ---------------------------------------------
-- 4. 更新现有权限数据的子系统编码（可选）
-- 将现有菜单数据关联到PC端
-- ---------------------------------------------
UPDATE sys_permission SET subsystem_code = 'PC' WHERE subsystem_code IS NULL;
