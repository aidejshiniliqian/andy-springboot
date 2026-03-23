package com.andy.service;

import com.andy.dto.PermissionCreateDTO;
import com.andy.dto.PermissionQueryDTO;
import com.andy.dto.PermissionUpdateDTO;
import com.andy.entity.SysPermission;
import com.andy.vo.PermissionTreeVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> getPermissionsByUserId(Long userId, String subsystemCode);

    List<String> getPermissionCodesByUserId(Long userId, String subsystemCode);

    Long createPermission(PermissionCreateDTO dto);

    void updatePermission(PermissionUpdateDTO dto);

    void deletePermission(Long id);

    SysPermission getPermissionById(Long id);

    Page<SysPermission> getPermissionPage(Page<SysPermission> page, PermissionQueryDTO dto);

    List<PermissionTreeVO> getAllPermissionTree(String subsystemCode);

    List<PermissionTreeVO> getActivePermissionTree(String subsystemCode);

    List<PermissionTreeVO> buildPermissionTree(List<SysPermission> permissions);
}
