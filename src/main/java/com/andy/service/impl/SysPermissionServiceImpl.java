package com.andy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.dto.PermissionCreateDTO;
import com.andy.dto.PermissionQueryDTO;
import com.andy.dto.PermissionUpdateDTO;
import com.andy.entity.SysPermission;
import com.andy.entity.SysRolePermission;
import com.andy.entity.SysUserRole;
import com.andy.mapper.SysPermissionMapper;
import com.andy.service.SysPermissionService;
import com.andy.service.SysRolePermissionService;
import com.andy.service.SysUserRoleService;
import com.andy.vo.PermissionTreeVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    private final SysUserRoleService userRoleService;
    private final SysRolePermissionService rolePermissionService;

    @Override
    public List<SysPermission> getPermissionsByUserId(Long userId, String subsystemCode) {
        List<SysUserRole> userRoles = userRoleService.list(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId)
        );
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).toList();
        List<SysRolePermission> rolePermissions = rolePermissionService.list(
                new LambdaQueryWrapper<SysRolePermission>().in(SysRolePermission::getRoleId, roleIds)
        );
        if (rolePermissions.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> permissionIds = rolePermissions.stream().map(SysRolePermission::getPermissionId).distinct().toList();
        
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysPermission::getId, permissionIds);
        if (StringUtils.hasText(subsystemCode)) {
            wrapper.eq(SysPermission::getSubsystemCode, subsystemCode);
        }
        return this.list(wrapper);
    }

    @Override
    public List<String> getPermissionCodesByUserId(Long userId, String subsystemCode) {
        List<SysPermission> permissions = getPermissionsByUserId(userId, subsystemCode);
        return permissions.stream().map(SysPermission::getPermissionCode).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createPermission(PermissionCreateDTO dto) {
        SysPermission permission = new SysPermission();
        BeanUtil.copyProperties(dto, permission);
        this.save(permission);
        return permission.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePermission(PermissionUpdateDTO dto) {
        SysPermission permission = this.getById(dto.getId());
        if (permission == null) {
            throw new RuntimeException("菜单不存在");
        }
        BeanUtil.copyProperties(dto, permission);
        this.updateById(permission);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(Long id) {
        List<SysPermission> children = this.list(
                new LambdaQueryWrapper<SysPermission>().eq(SysPermission::getParentId, id)
        );
        if (!children.isEmpty()) {
            throw new RuntimeException("存在子菜单，无法删除");
        }
        this.removeById(id);
        rolePermissionService.remove(
                new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getPermissionId, id)
        );
    }

    @Override
    public SysPermission getPermissionById(Long id) {
        SysPermission permission = this.getById(id);
        if (permission == null) {
            throw new RuntimeException("菜单不存在");
        }
        return permission;
    }

    @Override
    public Page<SysPermission> getPermissionPage(Page<SysPermission> page, PermissionQueryDTO dto) {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(dto.getPermissionName())) {
            wrapper.like(SysPermission::getPermissionName, dto.getPermissionName());
        }
        if (StringUtils.hasText(dto.getPermissionCode())) {
            wrapper.like(SysPermission::getPermissionCode, dto.getPermissionCode());
        }
        if (dto.getPermissionType() != null) {
            wrapper.eq(SysPermission::getPermissionType, dto.getPermissionType());
        }
        if (dto.getStatus() != null) {
            wrapper.eq(SysPermission::getStatus, dto.getStatus());
        }
        if (StringUtils.hasText(dto.getSubsystemCode())) {
            wrapper.eq(SysPermission::getSubsystemCode, dto.getSubsystemCode());
        }
        wrapper.orderByAsc(SysPermission::getSort);
        return this.page(page, wrapper);
    }

    @Override
    public List<PermissionTreeVO> getAllPermissionTree(String subsystemCode) {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(subsystemCode)) {
            wrapper.eq(SysPermission::getSubsystemCode, subsystemCode);
        }
        wrapper.orderByAsc(SysPermission::getSort);
        List<SysPermission> permissions = this.list(wrapper);
        return buildPermissionTree(permissions);
    }

    @Override
    public List<PermissionTreeVO> getActivePermissionTree(String subsystemCode) {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermission::getStatus, 1);
        if (StringUtils.hasText(subsystemCode)) {
            wrapper.eq(SysPermission::getSubsystemCode, subsystemCode);
        }
        wrapper.orderByAsc(SysPermission::getSort);
        List<SysPermission> permissions = this.list(wrapper);
        return buildPermissionTree(permissions);
    }

    @Override
    public List<PermissionTreeVO> buildPermissionTree(List<SysPermission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return Collections.emptyList();
        }
        
        Map<Long, List<SysPermission>> parentMap = permissions.stream()
                .collect(Collectors.groupingBy(SysPermission::getParentId));
        
        List<SysPermission> rootPermissions = parentMap.getOrDefault(0L, Collections.emptyList());
        
        return rootPermissions.stream()
                .map(permission -> convertToTreeVO(permission, parentMap))
                .collect(Collectors.toList());
    }

    private PermissionTreeVO convertToTreeVO(SysPermission permission, Map<Long, List<SysPermission>> parentMap) {
        PermissionTreeVO vo = new PermissionTreeVO();
        BeanUtil.copyProperties(permission, vo);
        
        List<SysPermission> children = parentMap.getOrDefault(permission.getId(), Collections.emptyList());
        if (!children.isEmpty()) {
            vo.setChildren(children.stream()
                    .map(child -> convertToTreeVO(child, parentMap))
                    .collect(Collectors.toList()));
        }
        
        return vo;
    }
}
