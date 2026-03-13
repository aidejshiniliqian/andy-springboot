# SQL 脚本说明

## 文件说明

| 文件名 | 说明 |
|--------|------|
| `init.sql` | 数据库表结构初始化脚本，包含所有表的创建语句 |
| `data.sql` | 初始数据脚本，包含默认的用户、角色、权限等数据 |

## 使用方式

### 1. 创建数据库

```sql
CREATE DATABASE andy_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 执行初始化脚本

先执行 `init.sql` 创建表结构，再执行 `data.sql` 导入初始数据。

```bash
# 使用 MySQL 命令行
mysql -u root -p andy_db < init.sql
mysql -u root -p andy_db < data.sql
```

## RBAC2 模型说明

本系统采用 RBAC2（基于角色的访问控制模型2）设计，包含以下核心表：

### 核心实体表
- `sys_user` - 用户表：存储系统用户信息
- `sys_role` - 角色表：存储角色信息
- `sys_permission` - 权限表：存储权限信息（菜单、按钮、接口）
- `sys_dept` - 部门表：存储组织架构信息

### 关联表
- `sys_user_role` - 用户角色关联：多对多关系
- `sys_role_permission` - 角色权限关联：多对多关系
- `sys_user_dept` - 用户部门关联：多对多关系

### 默认数据

#### 默认用户
| 用户名 | 密码 | 角色 |
|--------|------|------|
| admin | admin123 | 超级管理员 |
| user | admin123 | 普通用户 |
| dev | admin123 | 开发人员 |
| test | admin123 | 测试人员 |

#### 默认角色
- ROLE_ADMIN - 超级管理员
- ROLE_USER - 普通用户
- ROLE_DEPT_ADMIN - 部门管理员
- ROLE_TEST - 测试人员
- ROLE_DEV - 开发人员
