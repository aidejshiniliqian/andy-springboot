package com.example.demo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.system.dto.SysRoleDTO;
import com.example.demo.system.entity.SysRole;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    void saveRole(SysRoleDTO roleDTO);

    void updateRole(SysRoleDTO roleDTO);

    void deleteRole(Long id);

    void bindMenus(Long roleId, List<Long> menuIds);

    void unbindMenus(Long roleId, List<Long> menuIds);

    List<Long> getMenuIdsByRoleId(Long roleId);
}
