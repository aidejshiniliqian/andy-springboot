package com.andy.service.impl;

import com.andy.entity.system.SysPermission;
import com.andy.entity.system.SysRole;
import com.andy.entity.system.SysUser;
import com.andy.entity.system.SysUserRole;
import com.andy.mapper.SysUserMapper;
import com.andy.mapper.SysUserRoleMapper;
import com.andy.model.dto.UserDTO;
import com.andy.model.vo.PermissionVO;
import com.andy.model.vo.RoleVO;
import com.andy.model.vo.UserVO;
import com.andy.service.SysPermissionService;
import com.andy.service.SysRoleService;
import com.andy.service.SysUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserMapper sysUserMapper;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleService sysRoleService;
    private final SysPermissionService sysPermissionService;

    @Override
    public SysUser getByUsername(String username) {
        return sysUserMapper.selectByUsername(username);
    }

    @Override
    public List<String> getRoleCodesByUserId(Long userId) {
        return sysUserMapper.selectRoleCodesByUserId(userId);
    }

    @Override
    public List<String> getPermissionCodesByUserId(Long userId) {
        return sysUserMapper.selectPermissionCodesByUserId(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignRoles(Long userId, List<Long> roleIds) {
        sysUserRoleMapper.deleteByUserId(userId);
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        List<SysUserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        for (SysUserRole ur : userRoles) {
            sysUserRoleMapper.insert(ur);
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bindRoles(Long userId, List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        List<SysUserRole> existingList = sysUserRoleMapper.selectList(wrapper);
        Set<Long> existingRoleIds = existingList.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toSet());

        for (Long roleId : roleIds) {
            if (!existingRoleIds.contains(roleId)) {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                sysUserRoleMapper.insert(userRole);
            }
        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean unbindRoles(Long userId, List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return true;
        }
        LambdaQueryWrapper<SysUserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUserRole::getUserId, userId);
        wrapper.in(SysUserRole::getRoleId, roleIds);
        sysUserRoleMapper.delete(wrapper);
        return true;
    }

    @Override
    public UserVO getUserVOById(Long id) {
        SysUser user = this.getById(id);
        if (user == null) {
            return null;
        }
        UserVO vo = new UserVO();
        BeanUtils.copyProperties(user, vo);

        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>()
                        .eq(SysUserRole::getUserId, id)
        );
        List<Long> roleIds = userRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        vo.setRoleIds(roleIds);

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveUser(UserDTO userDTO) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        if (user.getStatus() == null) {
            user.setStatus(1);
        }
        boolean saved = this.save(user);

        if (saved && userDTO.getRoleIds() != null && !userDTO.getRoleIds().isEmpty()) {
            assignRoles(user.getId(), userDTO.getRoleIds());
        }
        return saved;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserDTO userDTO) {
        SysUser user = new SysUser();
        BeanUtils.copyProperties(userDTO, user);
        boolean updated = this.updateById(user);

        if (updated && userDTO.getRoleIds() != null) {
            assignRoles(user.getId(), userDTO.getRoleIds());
        }
        return updated;
    }

    @Override
    public List<UserVO> getUserVOList() {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getDeleted, 0);
        wrapper.orderByDesc(SysUser::getCreateTime);
        List<SysUser> users = this.list(wrapper);

        return users.stream().map(user -> {
            UserVO vo = new UserVO();
            BeanUtils.copyProperties(user, vo);
            return vo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<PermissionVO> getUserMenuTree(Long userId) {
        return getUserMenuTree(userId, null);
    }

    @Override
    public List<PermissionVO> getUserMenuTree(Long userId, String subsystemCode) {
        List<SysRole> roles = sysRoleService.getRolesByUserId(userId);
        if (CollectionUtils.isEmpty(roles)) {
            return new ArrayList<>();
        }

        Set<Long> commonPermissionIds = null;
        for (SysRole role : roles) {
            List<SysPermission> permissions = sysPermissionService.getPermissionsByRoleId(role.getId(), subsystemCode);
            Set<Long> permissionIds = permissions.stream()
                    .map(SysPermission::getId)
                    .collect(Collectors.toSet());

            if (commonPermissionIds == null) {
                commonPermissionIds = new HashSet<>(permissionIds);
            } else {
                commonPermissionIds.retainAll(permissionIds);
            }
        }

        if (CollectionUtils.isEmpty(commonPermissionIds)) {
            return new ArrayList<>();
        }

        List<SysPermission> commonPermissions = sysPermissionService.listByIds(commonPermissionIds);
        commonPermissions = commonPermissions.stream()
                .filter(p -> p.getType() == 1)
                .filter(p -> p.getStatus() == 1)
                .sorted(Comparator.comparing(SysPermission::getSort))
                .collect(Collectors.toList());

        return sysPermissionService.buildPermissionTree(commonPermissions);
    }
}
