package com.andy.controller;

import com.andy.common.result.CommonResult;
import com.andy.dto.SubsystemCreateDTO;
import com.andy.dto.SubsystemQueryDTO;
import com.andy.dto.SubsystemUpdateDTO;
import com.andy.entity.SysSubsystem;
import com.andy.service.SysSubsystemService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subsystem")
@RequiredArgsConstructor
@Tag(name = "子系统管理", description = "子系统管理相关接口")
public class SubsystemController {

    private final SysSubsystemService subsystemService;

    @PostMapping
    @Operation(summary = "创建子系统")
    public CommonResult<Long> createSubsystem(@Valid @RequestBody SubsystemCreateDTO dto) {
        Long id = subsystemService.createSubsystem(dto);
        return CommonResult.success(id);
    }

    @PutMapping
    @Operation(summary = "更新子系统")
    public CommonResult<Void> updateSubsystem(@Valid @RequestBody SubsystemUpdateDTO dto) {
        subsystemService.updateSubsystem(dto);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除子系统")
    public CommonResult<Void> deleteSubsystem(@Parameter(description = "子系统ID") @PathVariable Long id) {
        subsystemService.deleteSubsystem(id);
        return CommonResult.success();
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID查询子系统")
    public CommonResult<SysSubsystem> getSubsystemById(@Parameter(description = "子系统ID") @PathVariable Long id) {
        SysSubsystem subsystem = subsystemService.getById(id);
        return CommonResult.success(subsystem);
    }

    @GetMapping("/page")
    @Operation(summary = "分页查询子系统")
    public CommonResult<Page<SysSubsystem>> getSubsystemPage(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer current,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") Integer size,
            SubsystemQueryDTO dto) {
        Page<SysSubsystem> page = subsystemService.getSubsystemPage(new Page<>(current, size), dto);
        return CommonResult.success(page);
    }

    @GetMapping("/list")
    @Operation(summary = "查询有效子系统列表")
    public CommonResult<List<SysSubsystem>> getActiveSubsystems() {
        List<SysSubsystem> list = subsystemService.getActiveSubsystems();
        return CommonResult.success(list);
    }
}
