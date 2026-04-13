package com.andy.controller.system;

import com.andy.common.CommonResult;
import com.andy.model.dto.SubsystemDTO;
import com.andy.model.vo.SubsystemVO;
import com.andy.service.SysSubsystemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "子系统管理", description = "子系统相关接口")
@RestController
@RequestMapping("/system/subsystem")
@RequiredArgsConstructor
public class SysSubsystemController {

    private final SysSubsystemService sysSubsystemService;

    @Operation(summary = "获取所有子系统列表")
    @GetMapping("/list")
    public CommonResult<List<SubsystemVO>> list() {
        return CommonResult.success(sysSubsystemService.getSubsystemVOList());
    }

    @Operation(summary = "获取有效子系统列表")
    @GetMapping("/list/valid")
    public CommonResult<List<SubsystemVO>> listValid() {
        return CommonResult.success(sysSubsystemService.getValidSubsystemVOList());
    }

    @Operation(summary = "根据ID获取子系统")
    @GetMapping("/{id}")
    public CommonResult<SubsystemVO> getById(@Parameter(description = "子系统ID") @PathVariable Long id) {
        return CommonResult.success(sysSubsystemService.getSubsystemVOById(id));
    }

    @Operation(summary = "新增子系统")
    @PostMapping
    public CommonResult<Boolean> save(@Valid @RequestBody SubsystemDTO subsystemDTO) {
        return CommonResult.success(sysSubsystemService.saveSubsystem(subsystemDTO));
    }

    @Operation(summary = "更新子系统")
    @PutMapping
    public CommonResult<Boolean> update(@Valid @RequestBody SubsystemDTO subsystemDTO) {
        return CommonResult.success(sysSubsystemService.updateSubsystem(subsystemDTO));
    }

    @Operation(summary = "删除子系统")
    @DeleteMapping("/{id}")
    public CommonResult<Boolean> delete(@Parameter(description = "子系统ID") @PathVariable Long id) {
        return CommonResult.success(sysSubsystemService.removeById(id));
    }

    @Operation(summary = "获取所有子系统编码")
    @GetMapping("/codes")
    public CommonResult<List<String>> getAllCodes() {
        return CommonResult.success(sysSubsystemService.getAllCodes());
    }

    @Operation(summary = "校验子系统编码是否有效")
    @GetMapping("/check-code")
    public CommonResult<Boolean> checkCode(@Parameter(description = "子系统编码") @RequestParam String code) {
        return CommonResult.success(sysSubsystemService.isValidCode(code));
    }
}
