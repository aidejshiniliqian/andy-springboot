package com.andy.service;

import com.andy.dto.SubsystemCreateDTO;
import com.andy.dto.SubsystemQueryDTO;
import com.andy.dto.SubsystemUpdateDTO;
import com.andy.entity.SysSubsystem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysSubsystemService extends IService<SysSubsystem> {

    Long createSubsystem(SubsystemCreateDTO dto);

    void updateSubsystem(SubsystemUpdateDTO dto);

    void deleteSubsystem(Long id);

    Page<SysSubsystem> getSubsystemPage(Page<SysSubsystem> page, SubsystemQueryDTO dto);

    List<SysSubsystem> getActiveSubsystems();

    SysSubsystem getByCode(String subsystemCode);
}
