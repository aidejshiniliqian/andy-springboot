package com.andy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.andy.dto.UserBindRoleDTO;
import com.andy.dto.UserCreateDTO;
import com.andy.dto.UserQueryDTO;
import com.andy.dto.UserUpdateDTO;
import com.andy.entity.SysPermission;
import com.andy.entity.SysRole;
import com.andy.entity.SysUser;
import com.andy.entity.SysUserRole;
import com.andy.mapper.SysUserMapper;
import com.andy.service.SysPermissionService;
import com.andy.service.SysRoleService;
import com.andy.service.SysUserRoleService;
import com.andy.service.SysUserService;
import com.andy.vo.PermissionTreeVO;
import com.andy.vo.RoleVO;
import com.andy.vo.UserVO;
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
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService userRoleService;
    private final SysRoleService roleService;
    private final SysPermissionService permissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createUser(UserCreateDTO dto) {
        SysUser existingUser = this.getOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, dto.getUsername())
        );
        if (existingUser != null) {
            throw new RuntimeException("用户名已存在");
        }
        
        SysUser user = new SysUser();
        BeanUtil.copyProperties(dto, user);
        user.setPassword(BCrypt.hashpw(dto.getPassword()));
        this.save(user);
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(UserUpdateDTO dto) {
        SysUser user = this.getById(dto.getId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        if (StringUtils.hasText(dto.getUsername()) && !dto.getUsername().equals(user.getUsername())) {
            SysUser existingUser = this.getOne(
                    new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, dto.getUsername())
            );
            if (existingUser != null) {
                throw new RuntimeException("用户名已存在");
            }
        }
        
        BeanUtil.copyProperties(dto, user);
        if (StringUtils.hasText(dto.getPassword())) {
            user.setPassword(BCrypt.hashpw(dto.getPassword()));
        }
        this.updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        this.removeById(id);
        userRoleService.remove(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, id)
        );
    }

    @Override
    public UserVO getUserById(Long id) {
        SysUser user = this.getById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return convertToVO(user);
    }

    @Override
    public Page<UserVO> getUserPage(Page<SysUser> page, UserQueryDTO dto) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        if (dto != null) {
            if (StringUtils.hasText(dto.getUsername())) {
                wrapper.like(SysUser::getUsername, dto.getUsername());
            }
            if (StringUtils.hasText(dto.getNickname())) {
                wrapper.like(SysUser::getNickname, dto.getNickname());
            }
            if (StringUtils.hasText(dto.getEmail())) {
                wrapper.like(SysUser::getEmail, dto.getEmail());
            }
            if (StringUtils.hasText(dto.getPhone())) {
                wrapper.like(SysUser::getPhone, dto.getPhone());
            }
            if (dto.getStatus() != null) {
                wrapper.eq(SysUser::getStatus, dto.getStatus());
            }
        }
        wrapper.orderByDesc(SysUser::getCreateTime);
        Page<SysUser> userPage = this.page(page, wrapper);
        
        Page<UserVO> voPage = new Page<>(userPage.getCurrent(), userPage.getSize(), userPage.getTotal());
        List<UserVO> voList = userPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        voPage.setRecords(voList);
        return voPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindRoles(UserBindRoleDTO dto) {
        SysUser user = this.getById(dto.getUserId());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        userRoleService.remove(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, dto.getUserId())
        );
        
        for (Long roleId : dto.getRoleIds()) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(dto.getUserId());
            userRole.setRoleId(roleId);
            userRoleService.save(userRole);
        }
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        List<SysUserRole> userRoles = userRoleService.list(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId)
        );
        return userRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionTreeVO> getUserPermissionTree(Long userId, String subsystemCode) {
        List<SysPermission> permissions = permissionService.getPermissionsByUserId(userId, subsystemCode);
        if (permissions.isEmpty()) {
            return Collections.emptyList();
        }
        
        List<SysPermission> activePermissions = permissions.stream()
                .filter(p -> p.getStatus() != null && p.getStatus() == 1)
                .collect(Collectors.toList());
        
        return permissionService.buildPermissionTree(activePermissions);
    }

    private UserVO convertToVO(SysUser user) {
        UserVO vo = new UserVO();
        BeanUtil.copyProperties(user, vo);
        
        List<Long> roleIds = getRoleIdsByUserId(user.getId());
        vo.setRoleIds(roleIds);
        
        if (!roleIds.isEmpty()) {
            List<SysRole> roles = roleService.listByIds(roleIds);
            List<RoleVO> roleVOs = roles.stream()
                    .map(role -> {
                        RoleVO roleVO = new RoleVO();
                        BeanUtil.copyProperties(role, roleVO);
                        return roleVO;
                    })
                    .collect(Collectors.toList());
            vo.setRoles(roleVOs);
        } else {
            vo.setRoles(Collections.emptyList());
        }
        
        return vo;
    }
}
