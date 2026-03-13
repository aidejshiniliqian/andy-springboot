package com.andy.controller;

import com.andy.common.result.CommonResult;
import com.andy.dto.RoleBindPermissionDTO;
import com.andy.dto.RoleCreateDTO;
import com.andy.dto.RoleQueryDTO;
import com.andy.dto.RoleUpdateDTO;
import com.andy.entity.SysRole;
import com.andy.service.SysRoleService;
import com.andy.vo.RoleVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
@Tag(name = "角色管理", description = "角色管理相关接口")
public class RoleController {

    private final SysRoleService roleService;

    @PostMapping
    @Operation(summary = "创建角色")
    public CommonResult<Long> createRole(@Valid @RequestBody RoleCreateDTO dto) {
        Long id = roleService.createRole(dto);
        return CommonResult.success(id);
    }

    @PutMapping
    @Operation(summary = "更新角色")
    public CommonResult<Void> updateRole(@Valid @RequestBody RoleUpdateDTO dto) {
        roleService.updateRole(dto);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除角色")
    public CommonResult<Void> deleteRole(@Parameter(description = "角色ID") @PathVariable Long id) {
        roleService.deleteRole(id);
        return CommonResult.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询角色")
    public CommonResult<RoleVO> getRoleById(@Parameter(description = "角色ID") @PathVariable Long id) {
        RoleVO role = roleService.getRoleById(id);
        return CommonResult.success(role);
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询角色")
    public CommonResult<Page<RoleVO>> getRolePage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @RequestBody(required = false) RoleQueryDTO dto) {
        Page<SysRole> page = new Page<>(current, size);
        Page<RoleVO> result = roleService.getRolePage(page, dto);
        return CommonResult.success(result);
    }

    @PostMapping("/bind-permissions")
    @Operation(summary = "角色绑定菜单")
    public CommonResult<Void> bindPermissions(@Valid @RequestBody RoleBindPermissionDTO dto) {
        roleService.bindPermissions(dto);
        return CommonResult.success();
    }

    @PostMapping("/unbind-permissions")
    @Operation(summary = "角色解绑菜单")
    public CommonResult<Void> unbindPermissions(@Valid @RequestBody RoleBindPermissionDTO dto) {
        roleService.unbindPermissions(dto);
        return CommonResult.success();
    }

    @GetMapping("/{roleId}/permissions")
    @Operation(summary = "查询角色绑定的菜单ID列表")
    public CommonResult<List<Long>> getPermissionIdsByRoleId(
            @Parameter(description = "角色ID") @PathVariable Long roleId) {
        List<Long> permissionIds = roleService.getPermissionIdsByRoleId(roleId);
        return CommonResult.success(permissionIds);
    }
}
