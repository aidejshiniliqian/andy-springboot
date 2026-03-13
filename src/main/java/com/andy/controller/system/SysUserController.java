package com.andy.controller.system;

import com.andy.common.CommonResult;
import com.andy.entity.system.SysUser;
import com.andy.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "用户管理", description = "系统用户相关接口")
@RestController
@RequestMapping("/system/user")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService sysUserService;

    @Operation(summary = "获取用户列表")
    @GetMapping("/list")
    public CommonResult<List<SysUser>> list() {
        return CommonResult.success(sysUserService.list());
    }

    @Operation(summary = "根据ID获取用户")
    @GetMapping("/{id}")
    public CommonResult<SysUser> getById(@Parameter(description = "用户ID") @PathVariable Long id) {
        return CommonResult.success(sysUserService.getById(id));
    }

    @Operation(summary = "根据用户名获取用户")
    @GetMapping("/username/{username}")
    public CommonResult<SysUser> getByUsername(@Parameter(description = "用户名") @PathVariable String username) {
        return CommonResult.success(sysUserService.getByUsername(username));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public CommonResult<Boolean> save(@RequestBody SysUser user) {
        return CommonResult.success(sysUserService.save(user));
    }

    @Operation(summary = "更新用户")
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody SysUser user) {
        return CommonResult.success(sysUserService.updateById(user));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@Parameter(description = "用户ID") @PathVariable Long id) {
        return CommonResult.success(sysUserService.removeById(id));
    }

    @Operation(summary = "获取用户角色编码列表")
    @GetMapping("/{userId}/roles")
    public CommonResult<List<String>> getRoleCodes(@Parameter(description = "用户ID") @PathVariable Long userId) {
        return CommonResult.success(sysUserService.getRoleCodesByUserId(userId));
    }

    @Operation(summary = "分配用户角色")
    @PostMapping("/{userId}/roles")
    public CommonResult<Boolean> assignRoles(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestBody List<Long> roleIds) {
        return CommonResult.success(sysUserService.assignRoles(userId, roleIds));
    }

    @Operation(summary = "获取用户权限编码列表")
    @GetMapping("/{userId}/permissions")
    public CommonResult<List<String>> getPermissionCodes(@Parameter(description = "用户ID") @PathVariable Long userId) {
        return CommonResult.success(sysUserService.getPermissionCodesByUserId(userId));
    }
}
