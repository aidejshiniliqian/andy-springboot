package com.andy.controller.system;

import com.andy.common.CommonResult;
import com.andy.model.dto.PermissionDTO;
import com.andy.model.vo.PermissionVO;
import com.andy.service.SysPermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "菜单权限管理", description = "系统菜单权限相关接口")
@RestController
@RequestMapping("/system/permission")
@RequiredArgsConstructor
public class SysPermissionController {

    private final SysPermissionService sysPermissionService;

    @Operation(summary = "获取全部菜单树（包含禁用）")
    @GetMapping("/tree/all")
    public CommonResult<List<PermissionVO>> getAllPermissionTree() {
        return CommonResult.success(sysPermissionService.getAllPermissionTree());
    }

    @Operation(summary = "获取有效菜单树（仅启用的菜单，用于前端展示）")
    @GetMapping("/tree/valid")
    public CommonResult<List<PermissionVO>> getValidPermissionTree() {
        return CommonResult.success(sysPermissionService.getValidPermissionTree());
    }

    @Operation(summary = "根据ID获取权限")
    @GetMapping("/{id}")
    public CommonResult<PermissionVO> getById(@Parameter(description = "权限ID") @PathVariable Long id) {
        return CommonResult.success(sysPermissionService.getPermissionVOById(id));
    }

    @Operation(summary = "新增权限")
    @PostMapping
    public CommonResult<Boolean> save(@Valid @RequestBody PermissionDTO permissionDTO) {
        return CommonResult.success(sysPermissionService.savePermission(permissionDTO));
    }

    @Operation(summary = "更新权限")
    @PutMapping
    public CommonResult<Boolean> update(@Valid @RequestBody PermissionDTO permissionDTO) {
        return CommonResult.success(sysPermissionService.updatePermission(permissionDTO));
    }

    @Operation(summary = "删除权限")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@Parameter(description = "权限ID") @PathVariable Long id) {
        return CommonResult.success(sysPermissionService.removeById(id));
    }

    @Operation(summary = "获取角色的权限列表")
    @GetMapping("/role/{roleId}")
    public CommonResult<List<PermissionVO>> getPermissionsByRoleId(@Parameter(description = "角色ID") @PathVariable Long roleId) {
        return CommonResult.success(sysPermissionService.buildPermissionTree(
                sysPermissionService.getPermissionsByRoleId(roleId)));
    }
}
