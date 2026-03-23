package com.example.demo.system.controller;

import com.example.demo.common.result.CommonResult;
import com.example.demo.system.dto.SysSystemDTO;
import com.example.demo.system.entity.SysSystem;
import com.example.demo.system.service.SysSystemService;
import com.example.demo.system.vo.SysSystemVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/system")
@Tag(name = "子系统管理")
public class SysSystemController {

    private final SysSystemService sysSystemService;

    public SysSystemController(SysSystemService sysSystemService) {
        this.sysSystemService = sysSystemService;
    }

    @GetMapping("/list")
    @Operation(summary = "获取子系统列表")
    public CommonResult<List<SysSystemVO>> list() {
        List<SysSystem> systems = sysSystemService.list();
        List<SysSystemVO> result = systems.stream()
                .map(system -> {
                    SysSystemVO vo = new SysSystemVO();
                    BeanUtils.copyProperties(system, vo);
                    return vo;
                })
                .toList();
        return CommonResult.success(result);
    }

    @GetMapping("/list/active")
    @Operation(summary = "获取有效子系统列表")
    public CommonResult<List<SysSystemVO>> listActive() {
        return CommonResult.success(sysSystemService.listActive());
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取子系统")
    public CommonResult<SysSystemVO> getById(@PathVariable Long id) {
        SysSystem system = sysSystemService.getById(id);
        if (system == null) {
            return CommonResult.error("子系统不存在");
        }
        SysSystemVO vo = new SysSystemVO();
        BeanUtils.copyProperties(system, vo);
        return CommonResult.success(vo);
    }

    @GetMapping("/code/{systemCode}")
    @Operation(summary = "根据编码获取子系统")
    public CommonResult<SysSystemVO> getByCode(@PathVariable String systemCode) {
        SysSystemVO vo = sysSystemService.getByCode(systemCode);
        if (vo == null) {
            return CommonResult.error("子系统不存在");
        }
        return CommonResult.success(vo);
    }

    @PostMapping
    @Operation(summary = "新增子系统")
    public CommonResult<Void> save(@Valid @RequestBody SysSystemDTO systemDTO) {
        sysSystemService.saveSystem(systemDTO);
        return CommonResult.success();
    }

    @PutMapping
    @Operation(summary = "修改子系统")
    public CommonResult<Void> update(@Valid @RequestBody SysSystemDTO systemDTO) {
        sysSystemService.updateSystem(systemDTO);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除子系统")
    public CommonResult<Void> delete(@PathVariable Long id) {
        sysSystemService.deleteSystem(id);
        return CommonResult.success();
    }
}
