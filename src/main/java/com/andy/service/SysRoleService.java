package com.andy.service;

import com.andy.dto.RoleBindPermissionDTO;
import com.andy.dto.RoleCreateDTO;
import com.andy.dto.RoleQueryDTO;
import com.andy.dto.RoleUpdateDTO;
import com.andy.entity.SysRole;
import com.andy.vo.RoleVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysRoleService extends IService<SysRole> {

    List<SysRole> getRolesByUserId(Long userId);

    boolean checkRoleExclusion(Long userId, Long roleId);

    boolean checkRoleCardinality(Long roleId);

    Long createRole(RoleCreateDTO dto);

    void updateRole(RoleUpdateDTO dto);

    void deleteRole(Long id);

    RoleVO getRoleById(Long id);

    Page<RoleVO> getRolePage(Page<SysRole> page, RoleQueryDTO dto);

    void bindPermissions(RoleBindPermissionDTO dto);

    void unbindPermissions(RoleBindPermissionDTO dto);

    List<Long> getPermissionIdsByRoleId(Long roleId);
}
