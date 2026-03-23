package com.example.demo.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.system.dto.SysRoleDTO;
import com.example.demo.system.entity.SysRole;
import com.example.demo.system.entity.SysRoleMenu;
import com.example.demo.system.mapper.SysRoleMapper;
import com.example.demo.system.service.SysRoleMenuService;
import com.example.demo.system.service.SysRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMenuService sysRoleMenuService;

    public SysRoleServiceImpl(SysRoleMenuService sysRoleMenuService) {
        this.sysRoleMenuService = sysRoleMenuService;
    }

    @Override
    public void saveRole(SysRoleDTO roleDTO) {
        SysRole role = BeanUtil.copyProperties(roleDTO, SysRole.class);
        this.save(role);
    }

    @Override
    public void updateRole(SysRoleDTO roleDTO) {
        SysRole role = BeanUtil.copyProperties(roleDTO, SysRole.class);
        this.updateById(role);
    }

    @Override
    public void deleteRole(Long id) {
        this.removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void bindMenus(Long roleId, List<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return;
        }
        List<SysRoleMenu> roleMenus = new ArrayList<>();
        for (Long menuId : menuIds) {
            SysRoleMenu roleMenu = new SysRoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }
        sysRoleMenuService.saveBatch(roleMenus);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unbindMenus(Long roleId, List<Long> menuIds) {
        sysRoleMenuService.deleteByRoleIdAndMenuIds(roleId, menuIds);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        return sysRoleMenuService.getMenuIdsByRoleId(roleId);
    }
}
