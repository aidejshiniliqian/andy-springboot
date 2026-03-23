package com.andy.service.impl;

import com.andy.entity.system.SysRole;
import com.andy.entity.system.SysRolePermission;
import com.andy.mapper.SysRoleMapper;
import com.andy.mapper.SysRolePermissionMapper;
import com.andy.model.dto.RoleDTO;
import com.andy.model.vo.PermissionVO;
import com.andy.model.vo.RoleVO;
import com.andy.service.SysPermissionService;
import com.andy.service.SysRoleService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRolePermissionMapper sysRolePermissionMapper;
    private final SysPermissionService sysPermissionService;

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return sysRoleMapper.selectRolesByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignPermissions(Long roleId, List<Long> permissionIds) {
        sysRolePermissionMapper.deleteByRoleId(roleId);
        if (permissionIds == null || permissionIds.isEmpty()) {
            return true;
        }
        List<SysRolePermission> rolePermissions = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            SysRolePermission rolePermission = new SysRolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }
        for (SysRolePermission rp : rolePermissions) {
            sysRolePermissionMapper.insert(rp);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindPermissions(Long roleId, List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return true;
        }
        LambdaQueryWrapper<SysRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRolePermission::getRoleId, roleId);
        List<SysRolePermission> existingList = sysRolePermissionMapper.selectList(wrapper);
        Set<Long> existingPermissionIds = existingList.stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toSet());

        for (Long permissionId : permissionIds) {
            if (!existingPermissionIds.contains(permissionId)) {
                SysRolePermission rolePermission = new SysRolePermission();
                rolePermission.setRoleId(roleId);
                rolePermission.setPermissionId(permissionId);
                sysRolePermissionMapper.insert(rolePermission);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unbindPermissions(Long roleId, List<Long> permissionIds) {
        if (permissionIds == null || permissionIds.isEmpty()) {
            return true;
        }
        LambdaQueryWrapper<SysRolePermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRolePermission::getRoleId, roleId);
        wrapper.in(SysRolePermission::getPermissionId, permissionIds);
        sysRolePermissionMapper.delete(wrapper);
        return true;
    }

    @Override
    public RoleVO getRoleVOById(Long id) {
        SysRole role = this.getById(id);
        if (role == null) {
            return null;
        }
        RoleVO vo = new RoleVO();
        BeanUtils.copyProperties(role, vo);

        List<SysRolePermission> rolePermissions = sysRolePermissionMapper.selectList(
                new LambdaQueryWrapper<SysRolePermission>()
                        .eq(SysRolePermission::getRoleId, id)
        );
        List<Long> permissionIds = rolePermissions.stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toList());
        vo.setPermissionIds(permissionIds);

        return vo;
    }

    @Override
    public boolean saveRole(RoleDTO roleDTO) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleDTO, role);
        if (role.getStatus() == null) {
            role.setStatus(1);
        }
        if (role.getSort() == null) {
            role.setSort(0);
        }
        boolean saved = this.save(role);

        if (saved && roleDTO.getPermissionIds() != null && !roleDTO.getPermissionIds().isEmpty()) {
            assignPermissions(role.getId(), roleDTO.getPermissionIds());
        }
        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateRole(RoleDTO roleDTO) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(roleDTO, role);
        boolean updated = this.updateById(role);

        if (updated && roleDTO.getPermissionIds() != null) {
            assignPermissions(role.getId(), roleDTO.getPermissionIds());
        }
        return updated;
    }

    @Override
    public List<RoleVO> getRoleVOList() {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getDeleted, 0);
        wrapper.orderByAsc(SysRole::getSort);
        List<SysRole> roles = this.list(wrapper);

        return roles.stream().map(role -> {
            RoleVO vo = new RoleVO();
            BeanUtils.copyProperties(role, vo);
            return vo;
        }).collect(Collectors.toList());
    }
}
