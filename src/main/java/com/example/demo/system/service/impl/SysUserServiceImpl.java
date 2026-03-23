package com.example.demo.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.system.dto.SysUserDTO;
import com.example.demo.system.entity.SysUser;
import com.example.demo.system.entity.SysUserRole;
import com.example.demo.system.mapper.SysUserMapper;
import com.example.demo.system.service.SysMenuService;
import com.example.demo.system.service.SysRoleService;
import com.example.demo.system.service.SysUserRoleService;
import com.example.demo.system.service.SysUserService;
import com.example.demo.system.vo.SysMenuVO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserRoleService sysUserRoleService;
    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;
    private final PasswordEncoder passwordEncoder;

    public SysUserServiceImpl(SysUserRoleService sysUserRoleService,
                              SysRoleService sysRoleService,
                              SysMenuService sysMenuService,
                              PasswordEncoder passwordEncoder) {
        this.sysUserRoleService = sysUserRoleService;
        this.sysRoleService = sysRoleService;
        this.sysMenuService = sysMenuService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(SysUserDTO userDTO) {
        SysUser user = BeanUtil.copyProperties(userDTO, SysUser.class);
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        this.save(user);
    }

    @Override
    public void updateUser(SysUserDTO userDTO) {
        SysUser user = BeanUtil.copyProperties(userDTO, SysUser.class);
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            SysUser oldUser = this.getById(user.getId());
            if (oldUser != null) {
                user.setPassword(oldUser.getPassword());
            }
        }
        this.updateById(user);
    }

    @Override
    public void deleteUser(Long id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindRoles(Long userId, List<Long> roleIds) {
        sysUserRoleService.deleteByUserId(userId);
        if (CollUtil.isEmpty(roleIds)) {
            return;
        }
        List<SysUserRole> userRoles = new ArrayList<>();
        for (Long roleId : roleIds) {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRoles.add(userRole);
        }
        sysUserRoleService.saveBatch(userRoles);
    }

    @Override
    public List<Long> getRoleIdsByUserId(Long userId) {
        return sysUserRoleService.getRoleIdsByUserId(userId);
    }

    @Override
    public List<SysMenuVO> getUserMenuTree(Long userId) {
        List<Long> roleIds = getRoleIdsByUserId(userId);
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        Set<Long> menuIds = getIntersectionMenuIds(roleIds);
        return sysMenuService.getMenuTreeByMenuIds(new ArrayList<>(menuIds));
    }

    @Override
    public List<SysMenuVO> getUserMenuTreeBySystemCode(Long userId, String systemCode) {
        List<Long> roleIds = getRoleIdsByUserId(userId);
        if (CollUtil.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        Set<Long> menuIds = getIntersectionMenuIds(roleIds);
        return sysMenuService.getMenuTreeByMenuIdsAndSystemCode(new ArrayList<>(menuIds), systemCode);
    }

    private Set<Long> getIntersectionMenuIds(List<Long> roleIds) {
        Set<Long> result = null;
        for (Long roleId : roleIds) {
            List<Long> menuIds = sysRoleService.getMenuIdsByRoleId(roleId);
            Set<Long> menuIdSet = new HashSet<>(menuIds);
            if (result == null) {
                result = menuIdSet;
            } else {
                result.retainAll(menuIdSet);
            }
        }
        return result != null ? result : new HashSet<>();
    }
}
