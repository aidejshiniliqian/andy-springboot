package com.example.demo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.system.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuService extends IService<SysRoleMenu> {

    void deleteByRoleIdAndMenuIds(Long roleId, List<Long> menuIds);

    List<SysRoleMenu> getByRoleId(Long roleId);

    List<Long> getMenuIdsByRoleId(Long roleId);

    List<SysRoleMenu> getByMenuId(Long menuId);
}
