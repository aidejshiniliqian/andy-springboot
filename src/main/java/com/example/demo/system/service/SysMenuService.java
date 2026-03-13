package com.example.demo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.system.dto.SysMenuDTO;
import com.example.demo.system.entity.SysMenu;
import com.example.demo.system.vo.SysMenuVO;

import java.util.List;

public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuVO> listAllMenuTree();

    List<SysMenuVO> listActiveMenuTree();

    List<SysMenuVO> buildMenuTree(List<SysMenu> menus);

    void saveMenu(SysMenuDTO menuDTO);

    void updateMenu(SysMenuDTO menuDTO);

    void deleteMenu(Long id);

    List<SysMenuVO> getMenuTreeByMenuIds(List<Long> menuIds);
}
