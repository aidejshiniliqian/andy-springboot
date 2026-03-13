package com.andy.service;

import com.andy.entity.system.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> getPermissionsByRoleId(Long roleId);

    List<SysPermission> getPermissionsByUserId(Long userId);

    List<SysPermission> getPermissionTree();
}
