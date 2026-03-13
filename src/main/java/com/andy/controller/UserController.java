package com.andy.controller;

import com.andy.common.result.CommonResult;
import com.andy.dto.UserBindRoleDTO;
import com.andy.dto.UserCreateDTO;
import com.andy.dto.UserQueryDTO;
import com.andy.dto.UserUpdateDTO;
import com.andy.entity.SysUser;
import com.andy.service.SysUserService;
import com.andy.vo.PermissionTreeVO;
import com.andy.vo.UserVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "用户管理", description = "用户管理相关接口")
public class UserController {

    private final SysUserService userService;

    @PostMapping
    @Operation(summary = "创建用户")
    public CommonResult<Long> createUser(@Valid @RequestBody UserCreateDTO dto) {
        Long id = userService.createUser(dto);
        return CommonResult.success(id);
    }

    @PutMapping
    @Operation(summary = "更新用户")
    public CommonResult<Void> updateUser(@Valid @RequestBody UserUpdateDTO dto) {
        userService.updateUser(dto);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public CommonResult<Void> deleteUser(@Parameter(description = "用户ID") @PathVariable Long id) {
        userService.deleteUser(id);
        return CommonResult.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询用户")
    public CommonResult<UserVO> getUserById(@Parameter(description = "用户ID") @PathVariable Long id) {
        UserVO user = userService.getUserById(id);
        return CommonResult.success(user);
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询用户")
    public CommonResult<Page<UserVO>> getUserPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @RequestBody(required = false) UserQueryDTO dto) {
        Page<SysUser> page = new Page<>(current, size);
        Page<UserVO> result = userService.getUserPage(page, dto);
        return CommonResult.success(result);
    }

    @PostMapping("/bind-roles")
    @Operation(summary = "用户绑定角色")
    public CommonResult<Void> bindRoles(@Valid @RequestBody UserBindRoleDTO dto) {
        userService.bindRoles(dto);
        return CommonResult.success();
    }

    @GetMapping("/{userId}/roles")
    @Operation(summary = "查询用户绑定的角色ID列表")
    public CommonResult<List<Long>> getRoleIdsByUserId(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        List<Long> roleIds = userService.getRoleIdsByUserId(userId);
        return CommonResult.success(roleIds);
    }

    @GetMapping("/{userId}/permission-tree")
    @Operation(summary = "查询用户菜单树")
    public CommonResult<List<PermissionTreeVO>> getUserPermissionTree(
            @Parameter(description = "用户ID") @PathVariable Long userId) {
        List<PermissionTreeVO> tree = userService.getUserPermissionTree(userId);
        return CommonResult.success(tree);
    }
}
