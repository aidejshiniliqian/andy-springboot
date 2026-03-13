package com.andy.service;

import com.andy.entity.system.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRolesByUserId(Long userId);

    boolean assignPermissions(Long roleId, List<Long> permissionIds);
}
