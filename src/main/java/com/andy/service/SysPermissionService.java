package com.andy.service;

import com.andy.entity.system.SysPermission;
import com.andy.model.dto.PermissionDTO;
import com.andy.model.vo.PermissionVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> getPermissionsByRoleId(Long roleId);

    List<SysPermission> getPermissionsByUserId(Long userId);

    List<SysPermission> getPermissionTree();

    List<PermissionVO> getAllPermissionTree();

    List<PermissionVO> getValidPermissionTree();

    PermissionVO getPermissionVOById(Long id);

    boolean savePermission(PermissionDTO permissionDTO);

    boolean updatePermission(PermissionDTO permissionDTO);

    List<PermissionVO> buildPermissionTree(List<SysPermission> permissions);
}
