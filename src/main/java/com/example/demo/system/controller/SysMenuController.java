package com.example.demo.system.controller;

import com.example.demo.common.result.CommonResult;
import com.example.demo.system.dto.SysMenuDTO;
import com.example.demo.system.entity.SysMenu;
import com.example.demo.system.service.SysMenuService;
import com.example.demo.system.vo.SysMenuVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
@Tag(name = "菜单管理")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    public SysMenuController(SysMenuService sysMenuService) {
        this.sysMenuService = sysMenuService;
    }

    @GetMapping("/list")
    @Operation(summary = "获取菜单列表")
    public CommonResult<List<SysMenuVO>> list() {
        List<SysMenu> menus = sysMenuService.list();
        List<SysMenuVO> result = menus.stream()
                .map(menu -> {
                    SysMenuVO vo = new SysMenuVO();
                    BeanUtils.copyProperties(menu, vo);
                    return vo;
                })
                .toList();
        return CommonResult.success(result);
    }

    @GetMapping("/tree/all")
    @Operation(summary = "获取全部菜单树")
    public CommonResult<List<SysMenuVO>> getAllMenuTree() {
        return CommonResult.success(sysMenuService.listAllMenuTree());
    }

    @GetMapping("/tree/active")
    @Operation(summary = "获取有效菜单树")
    public CommonResult<List<SysMenuVO>> getActiveMenuTree() {
        return CommonResult.success(sysMenuService.listActiveMenuTree());
    }

    @GetMapping("/tree/all/{systemCode}")
    @Operation(summary = "根据子系统编码获取全部菜单树")
    public CommonResult<List<SysMenuVO>> getAllMenuTreeBySystemCode(@PathVariable String systemCode) {
        return CommonResult.success(sysMenuService.listAllMenuTreeBySystemCode(systemCode));
    }

    @GetMapping("/tree/active/{systemCode}")
    @Operation(summary = "根据子系统编码获取有效菜单树")
    public CommonResult<List<SysMenuVO>> getActiveMenuTreeBySystemCode(@PathVariable String systemCode) {
        return CommonResult.success(sysMenuService.listActiveMenuTreeBySystemCode(systemCode));
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取菜单")
    public CommonResult<SysMenuVO> getById(@PathVariable Long id) {
        SysMenu menu = sysMenuService.getById(id);
        if (menu == null) {
            return CommonResult.error("菜单不存在");
        }
        SysMenuVO vo = new SysMenuVO();
        BeanUtils.copyProperties(menu, vo);
        return CommonResult.success(vo);
    }

    @PostMapping
    @Operation(summary = "新增菜单")
    public CommonResult<Void> save(@Valid @RequestBody SysMenuDTO menuDTO) {
        sysMenuService.saveMenu(menuDTO);
        return CommonResult.success();
    }

    @PutMapping
    @Operation(summary = "修改菜单")
    public CommonResult<Void> update(@Valid @RequestBody SysMenuDTO menuDTO) {
        sysMenuService.updateMenu(menuDTO);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除菜单")
    public CommonResult<Void> delete(@PathVariable Long id) {
        sysMenuService.deleteMenu(id);
        return CommonResult.success();
    }
}
