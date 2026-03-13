package com.andy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.dto.RoleBindPermissionDTO;
import com.andy.dto.RoleCreateDTO;
import com.andy.dto.RoleQueryDTO;
import com.andy.dto.RoleUpdateDTO;
import com.andy.entity.SysRole;
import com.andy.entity.SysRoleCardinality;
import com.andy.entity.SysRoleExclusion;
import com.andy.entity.SysRolePermission;
import com.andy.entity.SysUserRole;
import com.andy.mapper.SysRoleMapper;
import com.andy.service.SysRoleCardinalityService;
import com.andy.service.SysRoleExclusionService;
import com.andy.service.SysRolePermissionService;
import com.andy.service.SysRoleService;
import com.andy.service.SysUserRoleService;
import com.andy.vo.RoleVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysUserRoleService userRoleService;
    private final SysRoleExclusionService roleExclusionService;
    private final SysRoleCardinalityService roleCardinalityService;
    private final SysRolePermissionService rolePermissionService;

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        List<SysUserRole> userRoles = userRoleService.list(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId)
        );
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).toList();
        return this.listByIds(roleIds);
    }

    @Override
    public boolean checkRoleExclusion(Long userId, Long roleId) {
        List<SysRole> currentRoles = getRolesByUserId(userId);
        if (currentRoles.isEmpty()) {
            return true;
        }
        List<Long> currentRoleIds = currentRoles.stream().map(SysRole::getId).toList();
        List<SysRoleExclusion> exclusions = roleExclusionService.list(
                new LambdaQueryWrapper<SysRoleExclusion>()
                        .in(SysRoleExclusion::getRoleId, currentRoleIds)
                        .eq(SysRoleExclusion::getExcludeRoleId, roleId)
        );
        return exclusions.isEmpty();
    }

    @Override
    public boolean checkRoleCardinality(Long roleId) {
        SysRoleCardinality cardinality = roleCardinalityService.getOne(
                new LambdaQueryWrapper<SysRoleCardinality>().eq(SysRoleCardinality::getRoleId, roleId)
        );
        if (cardinality == null || cardinality.getMaxCount() == 0) {
            return true;
        }
        return cardinality.getCurrentCount() < cardinality.getMaxCount();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createRole(RoleCreateDTO dto) {
        SysRole role = new SysRole();
        BeanUtil.copyProperties(dto, role);
        this.save(role);
        return role.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleUpdateDTO dto) {
        SysRole role = this.getById(dto.getId());
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }
        BeanUtil.copyProperties(dto, role);
        this.updateById(role);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long id) {
        this.removeById(id);
        userRoleService.remove(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getRoleId, id)
        );
        rolePermissionService.remove(
                new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, id)
        );
    }

    @Override
    public RoleVO getRoleById(Long id) {
        SysRole role = this.getById(id);
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }
        RoleVO vo = new RoleVO();
        BeanUtil.copyProperties(role, vo);
        vo.setPermissionIds(getPermissionIdsByRoleId(id));
        return vo;
    }

    @Override
    public Page<RoleVO> getRolePage(Page<SysRole> page, RoleQueryDTO dto) {
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        if (dto != null) {
            if (StringUtils.hasText(dto.getRoleName())) {
                wrapper.like(SysRole::getRoleName, dto.getRoleName());
            }
            if (StringUtils.hasText(dto.getRoleCode())) {
                wrapper.like(SysRole::getRoleCode, dto.getRoleCode());
            }
            if (dto.getStatus() != null) {
                wrapper.eq(SysRole::getStatus, dto.getStatus());
            }
        }
        wrapper.orderByAsc(SysRole::getSort);
        Page<SysRole> rolePage = this.page(page, wrapper);
        
        Page<RoleVO> voPage = new Page<>(rolePage.getCurrent(), rolePage.getSize(), rolePage.getTotal());
        List<RoleVO> voList = rolePage.getRecords().stream()
                .map(role -> {
                    RoleVO vo = new RoleVO();
                    BeanUtil.copyProperties(role, vo);
                    vo.setPermissionIds(getPermissionIdsByRoleId(role.getId()));
                    return vo;
                })
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindPermissions(RoleBindPermissionDTO dto) {
        SysRole role = this.getById(dto.getRoleId());
        if (role == null) {
            throw new RuntimeException("角色不存在");
        }
        
        for (Long permissionId : dto.getPermissionIds()) {
            SysRolePermission existing = rolePermissionService.getOne(
                    new LambdaQueryWrapper<SysRolePermission>()
                            .eq(SysRolePermission::getRoleId, dto.getRoleId())
                            .eq(SysRolePermission::getPermissionId, permissionId)
            );
            if (existing == null) {
                SysRolePermission rolePermission = new SysRolePermission();
                rolePermission.setRoleId(dto.getRoleId());
                rolePermission.setPermissionId(permissionId);
                rolePermissionService.save(rolePermission);
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unbindPermissions(RoleBindPermissionDTO dto) {
        rolePermissionService.remove(
                new LambdaQueryWrapper<SysRolePermission>()
                        .eq(SysRolePermission::getRoleId, dto.getRoleId())
                        .in(SysRolePermission::getPermissionId, dto.getPermissionIds())
        );
    }

    @Override
    public List<Long> getPermissionIdsByRoleId(Long roleId) {
        List<SysRolePermission> rolePermissions = rolePermissionService.list(
                new LambdaQueryWrapper<SysRolePermission>().eq(SysRolePermission::getRoleId, roleId)
        );
        return rolePermissions.stream()
                .map(SysRolePermission::getPermissionId)
                .collect(Collectors.toList());
    }
}
