package com.andy.controller.system;

import com.andy.common.CommonResult;
import com.andy.model.dto.RoleDTO;
import com.andy.model.vo.RoleVO;
import com.andy.service.SysRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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
    public CommonResult<List<RoleVO>> list() {
        return CommonResult.success(sysRoleService.getRoleVOList());
    }

    @Operation(summary = "根据ID获取角色")
    @GetMapping("/{id}")
    public CommonResult<RoleVO> getById(@Parameter(description = "角色ID") @PathVariable Long id) {
        return CommonResult.success(sysRoleService.getRoleVOById(id));
    }

    @Operation(summary = "新增角色")
    @PostMapping
    public CommonResult<Boolean> save(@Valid @RequestBody RoleDTO roleDTO) {
        return CommonResult.success(sysRoleService.saveRole(roleDTO));
    }

    @Operation(summary = "更新角色")
    @PutMapping
    public CommonResult<Boolean> update(@Valid @RequestBody RoleDTO roleDTO) {
        return CommonResult.success(sysRoleService.updateRole(roleDTO));
    }

    @Operation(summary = "删除角色")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@Parameter(description = "角色ID") @PathVariable Long id) {
        return CommonResult.success(sysRoleService.removeById(id));
    }

    @Operation(summary = "获取用户的角色列表")
    @GetMapping("/user/{userId}")
    public CommonResult<List<RoleVO>> getRolesByUserId(@Parameter(description = "用户ID") @PathVariable Long userId) {
        return CommonResult.success(sysRoleService.getRolesByUserId(userId).stream()
                .map(role -> {
                    RoleVO vo = new RoleVO();
                    vo.setId(role.getId());
                    vo.setRoleCode(role.getRoleCode());
                    vo.setRoleName(role.getRoleName());
                    vo.setDescription(role.getDescription());
                    vo.setStatus(role.getStatus());
                    vo.setSort(role.getSort());
                    return vo;
                }).toList());
    }

    @Operation(summary = "分配角色权限（全量替换）")
    @PostMapping("/{roleId}/permissions")
    public CommonResult<Boolean> assignPermissions(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @RequestBody List<Long> permissionIds) {
        return CommonResult.success(sysRoleService.assignPermissions(roleId, permissionIds));
    }

    @Operation(summary = "绑定角色权限（增量绑定）")
    @PostMapping("/{roleId}/permissions/bind")
    public CommonResult<Boolean> bindPermissions(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @RequestBody List<Long> permissionIds) {
        return CommonResult.success(sysRoleService.bindPermissions(roleId, permissionIds));
    }

    @Operation(summary = "解绑角色权限（物理删除）")
    @PostMapping("/{roleId}/permissions/unbind")
    public CommonResult<Boolean> unbindPermissions(
            @Parameter(description = "角色ID") @PathVariable Long roleId,
            @RequestBody List<Long> permissionIds) {
        return CommonResult.success(sysRoleService.unbindPermissions(roleId, permissionIds));
    }
}
