package com.andy.service;

import com.andy.entity.system.SysSubsystem;
import com.andy.model.dto.SubsystemDTO;
import com.andy.model.vo.SubsystemVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysSubsystemService extends IService<SysSubsystem> {

    List<SubsystemVO> getSubsystemVOList();

    List<SubsystemVO> getValidSubsystemVOList();

    SubsystemVO getSubsystemVOById(Long id);

    boolean saveSubsystem(SubsystemDTO subsystemDTO);

    boolean updateSubsystem(SubsystemDTO subsystemDTO);

    List<String> getAllCodes();

    boolean isValidCode(String code);
}
