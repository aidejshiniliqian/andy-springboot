package com.andy.controller.system;

import com.andy.common.CommonResult;
import com.andy.entity.system.SysPermission;
import com.andy.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "权限管理", description = "系统权限相关接口")
@RestController
@RequestMapping("/system/permission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @Operation(summary = "获取权限列表")
    @GetMapping("/list")
    public CommonResult<List<SysPermission>> list() {
        return CommonResult.success(sysPermissionService.list());
    }

    @Operation(summary = "获取权限树")
    @GetMapping("/tree")
    public CommonResult<List<SysPermission>> tree() {
        return CommonResult.success(sysPermissionService.getPermissionTree());
    }

    @Operation(summary = "根据ID获取权限")
    @GetMapping("/{id}")
    public CommonResult<SysPermission> getById(@Parameter(description = "权限ID") @PathVariable Long id) {
        return CommonResult.success(sysPermissionService.getById(id));
    }

    @Operation(summary = "新增权限")
    @PostMapping
    public CommonResult<Boolean> save(@RequestBody SysPermission permission) {
        return CommonResult.success(sysPermissionService.save(permission));
    }

    @Operation(summary = "更新权限")
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody SysPermission permission) {
        return CommonResult.success(sysPermissionService.updateById(permission));
    }

    @Operation(summary = "删除权限")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@Parameter(description = "权限ID") @PathVariable Long id) {
        return CommonResult.success(sysPermissionService.removeById(id));
    }

    @Operation(summary = "获取角色的权限列表")
    @GetMapping("/role/{roleId}")
    public CommonResult<List<SysPermission>> getPermissionsByRoleId(@Parameter(description = "角色ID") @PathVariable Long roleId) {
        return CommonResult.success(sysPermissionService.getPermissionsByRoleId(roleId));
    }

    @Operation(summary = "获取用户的权限列表")
    @GetMapping("/user/{userId}")
    public CommonResult<List<SysPermission>> getPermissionsByUserId(@Parameter(description = "用户ID") @PathVariable Long userId) {
        return CommonResult.success(sysPermissionService.getPermissionsByUserId(userId));
    }
}
