package com.andy.controller;

import com.andy.common.result.CommonResult;
import com.andy.dto.PermissionCreateDTO;
import com.andy.dto.PermissionQueryDTO;
import com.andy.dto.PermissionUpdateDTO;
import com.andy.entity.SysPermission;
import com.andy.service.SysPermissionService;
import com.andy.vo.PermissionTreeVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permission")
@RequiredArgsConstructor
@Tag(name = "菜单管理", description = "菜单管理相关接口")
public class PermissionController {

    private final SysPermissionService permissionService;

    @PostMapping
    @Operation(summary = "创建菜单")
    public CommonResult<Long> createPermission(@Valid @RequestBody PermissionCreateDTO dto) {
        Long id = permissionService.createPermission(dto);
        return CommonResult.success(id);
    }

    @PutMapping
    @Operation(summary = "更新菜单")
    public CommonResult<Void> updatePermission(@Valid @RequestBody PermissionUpdateDTO dto) {
        permissionService.updatePermission(dto);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除菜单")
    public CommonResult<Void> deletePermission(@Parameter(description = "菜单ID") @PathVariable Long id) {
        permissionService.deletePermission(id);
        return CommonResult.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询菜单")
    public CommonResult<SysPermission> getPermissionById(@Parameter(description = "菜单ID") @PathVariable Long id) {
        SysPermission permission = permissionService.getPermissionById(id);
        return CommonResult.success(permission);
    }

    @PostMapping("/page")
    @Operation(summary = "分页查询菜单")
    public CommonResult<Page<SysPermission>> getPermissionPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            @RequestBody(required = false) PermissionQueryDTO dto) {
        Page<SysPermission> page = new Page<>(current, size);
        Page<SysPermission> result = permissionService.getPermissionPage(page, dto);
        return CommonResult.success(result);
    }

    @GetMapping("/tree/all")
    @Operation(summary = "查询全部菜单树")
    public CommonResult<List<PermissionTreeVO>> getAllPermissionTree(
            @Parameter(description = "子系统编码") @RequestParam(required = false) String subsystemCode) {
        List<PermissionTreeVO> tree = permissionService.getAllPermissionTree(subsystemCode);
        return CommonResult.success(tree);
    }

    @GetMapping("/tree/active")
    @Operation(summary = "查询有效菜单树")
    public CommonResult<List<PermissionTreeVO>> getActivePermissionTree(
            @Parameter(description = "子系统编码") @RequestParam(required = false) String subsystemCode) {
        List<PermissionTreeVO> tree = permissionService.getActivePermissionTree(subsystemCode);
        return CommonResult.success(tree);
    }
}
