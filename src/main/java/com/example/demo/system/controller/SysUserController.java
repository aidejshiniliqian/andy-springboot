package com.example.demo.system.controller;

import com.example.demo.common.result.CommonResult;
import com.example.demo.system.dto.SysUserDTO;
import com.example.demo.system.entity.SysUser;
import com.example.demo.system.service.SysUserService;
import com.example.demo.system.vo.SysMenuVO;
import com.example.demo.system.vo.SysUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/user")
@Tag(name = "用户管理")
public class SysUserController {

    private final SysUserService sysUserService;

    public SysUserController(SysUserService sysUserService) {
        this.sysUserService = sysUserService;
    }

    @GetMapping("/list")
    @Operation(summary = "获取用户列表")
    public CommonResult<List<SysUserVO>> list() {
        List<SysUser> users = sysUserService.list();
        List<SysUserVO> result = users.stream()
                .map(user -> {
                    SysUserVO vo = new SysUserVO();
                    BeanUtils.copyProperties(user, vo);
                    return vo;
                })
                .toList();
        return CommonResult.success(result);
    }

    @GetMapping("/{id}")
    @Operation(summary = "根据ID获取用户")
    public CommonResult<SysUserVO> getById(@PathVariable Long id) {
        SysUser user = sysUserService.getById(id);
        if (user == null) {
            return CommonResult.error("用户不存在");
        }
        SysUserVO vo = new SysUserVO();
        BeanUtils.copyProperties(user, vo);
        return CommonResult.success(vo);
    }

    @PostMapping
    @Operation(summary = "新增用户")
    public CommonResult<Void> save(@Valid @RequestBody SysUserDTO userDTO) {
        sysUserService.saveUser(userDTO);
        return CommonResult.success();
    }

    @PutMapping
    @Operation(summary = "修改用户")
    public CommonResult<Void> update(@Valid @RequestBody SysUserDTO userDTO) {
        sysUserService.updateUser(userDTO);
        return CommonResult.success();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除用户")
    public CommonResult<Void> delete(@PathVariable Long id) {
        sysUserService.deleteUser(id);
        return CommonResult.success();
    }

    @PostMapping("/{userId}/bindRoles")
    @Operation(summary = "用户绑定角色")
    public CommonResult<Void> bindRoles(@PathVariable Long userId, @RequestBody List<Long> roleIds) {
        sysUserService.bindRoles(userId, roleIds);
        return CommonResult.success();
    }

    @GetMapping("/{userId}/roleIds")
    @Operation(summary = "获取用户绑定的角色ID列表")
    public CommonResult<List<Long>> getRoleIdsByUserId(@PathVariable Long userId) {
        return CommonResult.success(sysUserService.getRoleIdsByUserId(userId));
    }

    @GetMapping("/{userId}/menuTree")
    @Operation(summary = "获取用户菜单树")
    public CommonResult<List<SysMenuVO>> getUserMenuTree(@PathVariable Long userId) {
        return CommonResult.success(sysUserService.getUserMenuTree(userId));
    }
}
