package com.andy.service.impl;

import com.andy.entity.system.SysPermission;
import com.andy.mapper.SysPermissionMapper;
import com.andy.service.SysPermissionService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
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
}
