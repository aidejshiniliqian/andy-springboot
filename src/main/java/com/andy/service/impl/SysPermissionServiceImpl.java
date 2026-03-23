package com.andy.service.impl;

import com.andy.entity.system.SysPermission;
import com.andy.mapper.SysPermissionMapper;
import com.andy.model.dto.PermissionDTO;
import com.andy.model.vo.PermissionVO;
import com.andy.service.SysPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {

    private final SysPermissionMapper sysPermissionMapper;

    @Override
    public List<SysPermission> getPermissionsByRoleId(Long roleId) {
        return sysPermissionMapper.selectPermissionsByRoleId(roleId);
    }

    @Override
    public List<SysPermission> getPermissionsByUserId(Long userId) {
        return sysPermissionMapper.selectPermissionsByUserId(userId);
    }

    @Override
    public List<SysPermission> getPermissionTree() {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermission::getStatus, 1);
        wrapper.eq(SysPermission::getDeleted, 0);
        wrapper.orderByAsc(SysPermission::getSort);
        List<SysPermission> allPermissions = this.list(wrapper);
        return buildTree(allPermissions, 0L);
    }

    @Override
    public List<PermissionVO> getAllPermissionTree() {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermission::getDeleted, 0);
        wrapper.orderByAsc(SysPermission::getSort);
        List<SysPermission> allPermissions = this.list(wrapper);
        return buildPermissionTree(allPermissions);
    }

    @Override
    public List<PermissionVO> getValidPermissionTree() {
        LambdaQueryWrapper<SysPermission> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysPermission::getStatus, 1);
        wrapper.eq(SysPermission::getDeleted, 0);
        wrapper.orderByAsc(SysPermission::getSort);
        List<SysPermission> validPermissions = this.list(wrapper);
        return buildPermissionTree(validPermissions);
    }

    @Override
    public PermissionVO getPermissionVOById(Long id) {
        SysPermission permission = this.getById(id);
        if (permission == null) {
            return null;
        }
        PermissionVO vo = new PermissionVO();
        BeanUtils.copyProperties(permission, vo);
        return vo;
    }

    @Override
    public boolean savePermission(PermissionDTO permissionDTO) {
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(permissionDTO, permission);
        if (permission.getParentId() == null) {
            permission.setParentId(0L);
        }
        if (permission.getStatus() == null) {
            permission.setStatus(1);
        }
        if (permission.getSort() == null) {
            permission.setSort(0);
        }
        return this.save(permission);
    }

    @Override
    public boolean updatePermission(PermissionDTO permissionDTO) {
        SysPermission permission = new SysPermission();
        BeanUtils.copyProperties(permissionDTO, permission);
        return this.updateById(permission);
    }

    @Override
    public List<PermissionVO> buildPermissionTree(List<SysPermission> permissions) {
        if (permissions == null || permissions.isEmpty()) {
            return new ArrayList<>();
        }
        List<PermissionVO> voList = permissions.stream().map(p -> {
            PermissionVO vo = new PermissionVO();
            BeanUtils.copyProperties(p, vo);
            return vo;
        }).collect(Collectors.toList());
        return buildVOTree(voList, 0L);
    }

    private List<SysPermission> buildTree(List<SysPermission> permissions, Long parentId) {
        Map<Long, List<SysPermission>> parentIdMap = permissions.stream()
                .collect(Collectors.groupingBy(SysPermission::getParentId));
        return buildTreeRecursive(parentIdMap, parentId);
    }

    private List<SysPermission> buildTreeRecursive(Map<Long, List<SysPermission>> parentIdMap, Long parentId) {
        List<SysPermission> children = parentIdMap.getOrDefault(parentId, new ArrayList<>());
        for (SysPermission child : children) {
            child.setChildren(buildTreeRecursive(parentIdMap, child.getId()));
        }
        return children;
    }

    private List<PermissionVO> buildVOTree(List<PermissionVO> permissions, Long parentId) {
        Map<Long, List<PermissionVO>> parentIdMap = permissions.stream()
                .collect(Collectors.groupingBy(PermissionVO::getParentId));
        return buildVOTreeRecursive(parentIdMap, parentId);
    }

    private List<PermissionVO> buildVOTreeRecursive(Map<Long, List<PermissionVO>> parentIdMap, Long parentId) {
        List<PermissionVO> children = parentIdMap.getOrDefault(parentId, new ArrayList<>());
        for (PermissionVO child : children) {
            child.setChildren(buildVOTreeRecursive(parentIdMap, child.getId()));
        }
        return children;
    }
}
