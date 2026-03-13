package com.andy.service;

import com.andy.entity.system.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    List<String> getRoleCodesByUserId(Long userId);

    List<String> getPermissionCodesByUserId(Long userId);

    boolean assignRoles(Long userId, List<Long> roleIds);
}
