package com.andy.service;

import com.andy.entity.system.SysRole;
import com.andy.model.dto.RoleDTO;
import com.andy.model.vo.RoleVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRolesByUserId(Long userId);

    boolean assignPermissions(Long roleId, List<Long> permissionIds);

    boolean bindPermissions(Long roleId, List<Long> permissionIds);

    boolean unbindPermissions(Long roleId, List<Long> permissionIds);

    RoleVO getRoleVOById(Long id);

    boolean saveRole(RoleDTO roleDTO);

    boolean updateRole(RoleDTO roleDTO);

    List<RoleVO> getRoleVOList();
}
