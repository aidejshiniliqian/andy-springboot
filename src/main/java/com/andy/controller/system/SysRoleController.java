package com.andy.controller.system;

import com.andy.common.CommonResult;
import com.andy.entity.system.SysRole;
import com.andy.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "角色管理", description = "系统角色相关接口")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @Operation(summary = "获取角色列表")
    @GetMapping("/list")
    public CommonResult<List<SysRole>> list() {
        return CommonResult.success(sysRoleService.list());
    }

    @Operation(summary = "根据ID获取角色")
    @GetMapping("/{id}")
    public CommonResult<SysRole> getById(@Parameter(description = "角色ID") @PathVariable Long id) {
        return CommonResult.success(sysRoleService.getById(id));
    }

    @Operation(summary = "新增角色")
    @PostMapping
    public CommonResult<Boolean> save(@RequestBody SysRole role) {
        return CommonResult.success(sysRoleService.save(role));
    }

    @Operation(summary = "更新角色")
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody SysRole role) {
        return CommonResult.success(sysRoleService.updateById(role));
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@Parameter(description = "角色ID") @PathVariable Long id) {
        return CommonResult.success(sysRoleService.removeById(id));
    }

    @Operation(summary = "获取用户的角色列表")
    @GetMapping("/user/{userId}")
    public CommonResult<List<SysRole>> getRolesByUserId(@Parameter(description = "用户ID") @PathVariable Long userId) {
        return CommonResult.success(sysRoleService.getRolesByUserId(userId));
    }

    @Operation(summary = "分配角色权限")
    @PostMapping("/{roleId}/permissions")
    public CommonResult<Boolean> assignPermissions(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @RequestBody List<Long> permissionIds) {
        return CommonResult.success(sysRoleService.assignPermissions(roleId, permissionIds));
    }
}
