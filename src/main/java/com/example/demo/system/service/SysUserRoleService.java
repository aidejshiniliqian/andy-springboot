package com.example.demo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.system.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleService extends IService<SysUserRole> {

    List<SysUserRole> getByUserId(Long userId);

    List<Long> getRoleIdsByUserId(Long userId);

    List<SysUserRole> getByRoleId(Long roleId);

    void deleteByUserId(Long userId);
}
