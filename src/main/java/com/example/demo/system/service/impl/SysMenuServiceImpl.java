package com.example.demo.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.system.dto.SysMenuDTO;
import com.example.demo.system.entity.SysMenu;
import com.example.demo.system.mapper.SysMenuMapper;
import com.example.demo.system.service.SysMenuService;
import com.example.demo.system.vo.SysMenuVO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenuVO> listAllMenuTree() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysMenu::getSort, SysMenu::getId);
        List<SysMenu> menus = this.list(wrapper);
        return buildMenuTree(menus);
    }

    @Override
    public List<SysMenuVO> listActiveMenuTree() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getStatus, 1);
        wrapper.orderByAsc(SysMenu::getSort, SysMenu::getId);
        List<SysMenu> menus = this.list(wrapper);
        return buildMenuTree(menus);
    }

    @Override
    public List<SysMenuVO> listAllMenuTreeBySystemCode(String systemCode) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getSystemCode, systemCode);
        wrapper.orderByAsc(SysMenu::getSort, SysMenu::getId);
        List<SysMenu> menus = this.list(wrapper);
        return buildMenuTree(menus);
    }

    @Override
    public List<SysMenuVO> listActiveMenuTreeBySystemCode(String systemCode) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getSystemCode, systemCode);
        wrapper.eq(SysMenu::getStatus, 1);
        wrapper.orderByAsc(SysMenu::getSort, SysMenu::getId);
        List<SysMenu> menus = this.list(wrapper);
        return buildMenuTree(menus);
    }

    @Override
    public List<SysMenuVO> buildMenuTree(List<SysMenu> menus) {
        if (CollUtil.isEmpty(menus)) {
            return new ArrayList<>();
        }
        List<SysMenuVO> menuVOs = menus.stream()
                .map(menu -> BeanUtil.copyProperties(menu, SysMenuVO.class))
                .collect(Collectors.toList());
        
        Map<Long, List<SysMenuVO>> parentMap = menuVOs.stream()
                .collect(Collectors.groupingBy(SysMenuVO::getParentId));
        
        List<SysMenuVO> rootMenus = parentMap.getOrDefault(0L, new ArrayList<>());
        
        for (SysMenuVO menu : menuVOs) {
            List<SysMenuVO> children = parentMap.get(menu.getId());
            menu.setChildren(children != null ? children : new ArrayList<>());
        }
        
        return rootMenus;
    }

    @Override
    public void saveMenu(SysMenuDTO menuDTO) {
        SysMenu menu = BeanUtil.copyProperties(menuDTO, SysMenu.class);
        this.save(menu);
    }

    @Override
    public void updateMenu(SysMenuDTO menuDTO) {
        SysMenu menu = BeanUtil.copyProperties(menuDTO, SysMenu.class);
        this.updateById(menu);
    }

    @Override
    public void deleteMenu(Long id) {
        this.removeById(id);
    }

    @Override
    public List<SysMenuVO> getMenuTreeByMenuIds(List<Long> menuIds) {
        if (CollUtil.isEmpty(menuIds)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysMenu::getId, menuIds);
        wrapper.eq(SysMenu::getStatus, 1);
        wrapper.orderByAsc(SysMenu::getSort, SysMenu::getId);
        List<SysMenu> menus = this.list(wrapper);
        return buildMenuTree(menus);
    }

    @Override
    public List<SysMenuVO> getMenuTreeByMenuIdsAndSystemCode(List<Long> menuIds, String systemCode) {
        if (CollUtil.isEmpty(menuIds)) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(SysMenu::getId, menuIds);
        wrapper.eq(SysMenu::getSystemCode, systemCode);
        wrapper.eq(SysMenu::getStatus, 1);
        wrapper.orderByAsc(SysMenu::getSort, SysMenu::getId);
        List<SysMenu> menus = this.list(wrapper);
        return buildMenuTree(menus);
    }
}
