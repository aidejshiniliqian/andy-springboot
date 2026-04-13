package com.andy.service;

import com.andy.entity.system.SysPermission;
import com.andy.model.dto.PermissionDTO;
import com.andy.model.vo.PermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> getPermissionsByRoleId(Long roleId);

    List<SysPermission> getPermissionsByRoleId(Long roleId, String subsystemCode);

    List<SysPermission> getPermissionsByUserId(Long userId);

    List<SysPermission> getPermissionsByUserId(Long userId, String subsystemCode);

    List<SysPermission> getPermissionTree();

    List<SysPermission> getPermissionTree(String subsystemCode);

    List<PermissionVO> getAllPermissionTree();

    List<PermissionVO> getAllPermissionTree(String subsystemCode);

    List<PermissionVO> getValidPermissionTree();

    List<PermissionVO> getValidPermissionTree(String subsystemCode);

    PermissionVO getPermissionVOById(Long id);

    boolean savePermission(PermissionDTO permissionDTO);

    boolean updatePermission(PermissionDTO permissionDTO);

    List<PermissionVO> buildPermissionTree(List<SysPermission> permissions);
}
