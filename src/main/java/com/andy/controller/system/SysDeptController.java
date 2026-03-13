package com.andy.controller.system;

import com.andy.common.CommonResult;
import com.andy.entity.system.SysDept;
import com.andy.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "部门管理", description = "系统部门相关接口")
@RestController
@RequestMapping("/system/dept")
@RequiredArgsConstructor
public class SysDeptController {

    private final SysDeptService sysDeptService;

    @Operation(summary = "获取部门列表")
    @GetMapping("/list")
    public CommonResult<List<SysDept>> list() {
        return CommonResult.success(sysDeptService.list());
    }

    @Operation(summary = "获取部门树")
    @GetMapping("/tree")
    public CommonResult<List<SysDept>> tree() {
        return CommonResult.success(sysDeptService.getDeptTree());
    }

    @Operation(summary = "根据ID获取部门")
    @GetMapping("/{id}")
    public CommonResult<SysDept> getById(@Parameter(description = "部门ID") @PathVariable Long id) {
        return CommonResult.success(sysDeptService.getById(id));
    }

    @Operation(summary = "新增部门")
    @PostMapping
    public CommonResult<Boolean> save(@RequestBody SysDept dept) {
        return CommonResult.success(sysDeptService.save(dept));
    }

    @Operation(summary = "更新部门")
    @PutMapping
    public CommonResult<Boolean> update(@RequestBody SysDept dept) {
        return CommonResult.success(sysDeptService.updateById(dept));
    }

    @Operation(summary = "删除部门")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@Parameter(description = "部门ID") @PathVariable Long id) {
        return CommonResult.success(sysDeptService.removeById(id));
    }

    @Operation(summary = "获取用户的部门列表")
    @GetMapping("/user/{userId}")
    public CommonResult<List<SysDept>> getDeptsByUserId(@Parameter(description = "用户ID") @PathVariable Long userId) {
        return CommonResult.success(sysDeptService.getDeptsByUserId(userId));
    }
}
