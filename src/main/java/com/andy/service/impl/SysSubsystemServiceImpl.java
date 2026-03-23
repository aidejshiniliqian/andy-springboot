package com.andy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.andy.dto.SubsystemCreateDTO;
import com.andy.dto.SubsystemQueryDTO;
import com.andy.dto.SubsystemUpdateDTO;
import com.andy.entity.SysPermission;
import com.andy.entity.SysSubsystem;
import com.andy.mapper.SysSubsystemMapper;
import com.andy.service.SysPermissionService;
import com.andy.service.SysSubsystemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SysSubsystemServiceImpl extends ServiceImpl<SysSubsystemMapper, SysSubsystem> implements SysSubsystemService {

    private final SysPermissionService permissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSubsystem(SubsystemCreateDTO dto) {
        SysSubsystem existing = this.getByCode(dto.getSubsystemCode());
        if (existing != null) {
            throw new RuntimeException("子系统编码已存在");
        }
        SysSubsystem subsystem = new SysSubsystem();
        BeanUtil.copyProperties(dto, subsystem);
        this.save(subsystem);
        return subsystem.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSubsystem(SubsystemUpdateDTO dto) {
        SysSubsystem subsystem = this.getById(dto.getId());
        if (subsystem == null) {
            throw new RuntimeException("子系统不存在");
        }
        if (StringUtils.hasText(dto.getSubsystemCode()) && !dto.getSubsystemCode().equals(subsystem.getSubsystemCode())) {
            SysSubsystem existing = this.getByCode(dto.getSubsystemCode());
            if (existing != null) {
                throw new RuntimeException("子系统编码已存在");
            }
        }
        BeanUtil.copyProperties(dto, subsystem);
        this.updateById(subsystem);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSubsystem(Long id) {
        SysSubsystem subsystem = this.getById(id);
        if (subsystem == null) {
            throw new RuntimeException("子系统不存在");
        }
        List<SysPermission> permissions = permissionService.list(
                new LambdaQueryWrapper<SysPermission>()
                        .eq(SysPermission::getSubsystemCode, subsystem.getSubsystemCode())
        );
        if (!permissions.isEmpty()) {
            throw new RuntimeException("子系统下存在菜单，无法删除");
        }
        this.removeById(id);
    }

    @Override
    public Page<SysSubsystem> getSubsystemPage(Page<SysSubsystem> page, SubsystemQueryDTO dto) {
        LambdaQueryWrapper<SysSubsystem> wrapper = new LambdaQueryWrapper<>();
        if (dto != null) {
            if (StringUtils.hasText(dto.getSubsystemName())) {
                wrapper.like(SysSubsystem::getSubsystemName, dto.getSubsystemName());
            }
            if (StringUtils.hasText(dto.getSubsystemCode())) {
                wrapper.like(SysSubsystem::getSubsystemCode, dto.getSubsystemCode());
            }
            if (dto.getStatus() != null) {
                wrapper.eq(SysSubsystem::getStatus, dto.getStatus());
            }
        }
        wrapper.orderByAsc(SysSubsystem::getSort);
        return this.page(page, wrapper);
    }

    @Override
    public List<SysSubsystem> getActiveSubsystems() {
        return this.list(
                new LambdaQueryWrapper<SysSubsystem>()
                        .eq(SysSubsystem::getStatus, 1)
                        .orderByAsc(SysSubsystem::getSort)
        );
    }

    @Override
    public SysSubsystem getByCode(String subsystemCode) {
        return this.getOne(
                new LambdaQueryWrapper<SysSubsystem>()
                        .eq(SysSubsystem::getSubsystemCode, subsystemCode)
        );
    }
}
