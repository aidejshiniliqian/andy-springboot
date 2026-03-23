package com.example.demo.system.controller;

import com.example.demo.common.result.CommonResult;
import com.example.demo.system.dto.SysRoleDTO;
import com.example.demo.system.entity.SysRole;
import com.example.demo.system.service.SysRoleService;
import com.example.demo.system.vo.SysRoleVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
@Tag(name = "角色管理")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    public SysRoleController(SysRoleService sysRoleService) {
        this.sysRoleService = sysRoleService;
    }

    @GetMapping("/list")
    @Operation(summary = "获取角色列表")
    public CommonResult<List<SysRoleVO>> list() {
        List<SysRole> roles = sysRoleService.list();
        List<SysRoleVO> result = roles.stream()
                .map(role -> {
                    SysRoleVO vo = new SysRoleVO();
                    BeanUtils.copyProperties(role, vo);
                    return vo;
                })
                .toList();
        return CommonResult.success(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取角色")
    public CommonResult<SysRoleVO> getById(@PathVariable Long id) {
        SysRole role = sysRoleService.getById(id);
        if (role == null) {
            return CommonResult.error("角色不存在");
        }
        SysRoleVO vo = new SysRoleVO();
        BeanUtils.copyProperties(role, vo);
        return CommonResult.success(vo);
    }

    @PostMapping
    @Operation(summary = "新增角色")
    public CommonResult<Void> save(@Valid @RequestBody SysRoleDTO roleDTO) {
        sysRoleService.saveRole(roleDTO);
        return CommonResult.success();
    }

    @PutMapping
    @Operation(summary = "修改角色")
    public CommonResult<Void> update(@Valid @RequestBody SysRoleDTO roleDTO) {
        sysRoleService.updateRole(roleDTO);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    public CommonResult<Void> delete(@PathVariable Long id) {
        sysRoleService.deleteRole(id);
        return CommonResult.success();
    }

    @PostMapping("/{roleId}/bindMenus")
    @Operation(summary = "角色绑定菜单")
    public CommonResult<Void> bindMenus(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        sysRoleService.bindMenus(roleId, menuIds);
        return CommonResult.success();
    }

    @PostMapping("/{roleId}/unbindMenus")
    @Operation(summary = "角色解绑菜单")
    public CommonResult<Void> unbindMenus(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        sysRoleService.unbindMenus(roleId, menuIds);
        return CommonResult.success();
    }

    @GetMapping("/{roleId}/menuIds")
    @Operation(summary = "获取角色绑定的菜单ID列表")
    public CommonResult<List<Long>> getMenuIdsByRoleId(@PathVariable Long roleId) {
        return CommonResult.success(sysRoleService.getMenuIdsByRoleId(roleId));
    }
}
