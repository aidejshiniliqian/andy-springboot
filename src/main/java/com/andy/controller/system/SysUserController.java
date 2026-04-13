package com.andy.controller.system;

import com.andy.common.CommonResult;
import com.andy.model.dto.UserDTO;
import com.andy.model.vo.PermissionVO;
import com.andy.model.vo.UserVO;
import com.andy.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public CommonResult<List<UserVO>> list() {
        return CommonResult.success(sysUserService.getUserVOList());
    }

    @Operation(summary = "根据ID获取用户")
    @GetMapping("/{id}")
    public CommonResult<UserVO> getById(@Parameter(description = "用户ID") @PathVariable Long id) {
        return CommonResult.success(sysUserService.getUserVOById(id));
    }

    @Operation(summary = "根据用户名获取用户")
    @GetMapping("/username/{username}")
    public CommonResult<UserVO> getByUsername(@Parameter(description = "用户名") @PathVariable String username) {
        return CommonResult.success(sysUserService.getUserVOById(
                sysUserService.getByUsername(username).getId()));
    }

    @Operation(summary = "新增用户")
    @PostMapping
    public CommonResult<Boolean> save(@Valid @RequestBody UserDTO userDTO) {
        return CommonResult.success(sysUserService.saveUser(userDTO));
    }

    @Operation(summary = "更新用户")
    @PutMapping
    public CommonResult<Boolean> update(@Valid @RequestBody UserDTO userDTO) {
        return CommonResult.success(sysUserService.updateUser(userDTO));
    }

    @Operation(summary = "删除用户")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@Parameter(description = "用户ID") @PathVariable Long id) {
        return CommonResult.success(sysUserService.removeById(id));
    }

    @Operation(summary = "获取用户角色编码列表")
    @GetMapping("/{userId}/role-codes")
    public CommonResult<List<String>> getRoleCodes(@Parameter(description = "用户ID") @PathVariable Long userId) {
        return CommonResult.success(sysUserService.getRoleCodesByUserId(userId));
    }

    @Operation(summary = "分配用户角色（全量替换）")
    @PostMapping("/{userId}/roles")
    public CommonResult<Boolean> assignRoles(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestBody List<Long> roleIds) {
        return CommonResult.success(sysUserService.assignRoles(userId, roleIds));
    }

    @Operation(summary = "绑定用户角色（增量绑定）")
    @PostMapping("/{userId}/roles/bind")
    public CommonResult<Boolean> bindRoles(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestBody List<Long> roleIds) {
        return CommonResult.success(sysUserService.bindRoles(userId, roleIds));
    }

    @Operation(summary = "解绑用户角色（物理删除）")
    @PostMapping("/{userId}/roles/unbind")
    public CommonResult<Boolean> unbindRoles(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @RequestBody List<Long> roleIds) {
        return CommonResult.success(sysUserService.unbindRoles(userId, roleIds));
    }

    @Operation(summary = "获取用户权限编码列表")
    @GetMapping("/{userId}/permission-codes")
    public CommonResult<List<String>> getPermissionCodes(@Parameter(description = "用户ID") @PathVariable Long userId) {
        return CommonResult.success(sysUserService.getPermissionCodesByUserId(userId));
    }

    @Operation(summary = "获取用户菜单树（多角色权限交集）")
    @GetMapping("/{userId}/menu-tree")
    public CommonResult<List<PermissionVO>> getUserMenuTree(
            @Parameter(description = "用户ID") @PathVariable Long userId,
            @Parameter(description = "子系统编码(pc:PC端,app:APP端)")
            @RequestParam(required = false) String subsystemCode) {
        return CommonResult.success(sysUserService.getUserMenuTree(userId, subsystemCode));
    }
}
