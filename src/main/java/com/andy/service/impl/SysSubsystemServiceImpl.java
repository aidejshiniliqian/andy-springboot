package com.andy.service.impl;

import com.andy.entity.system.SysSubsystem;
import com.andy.mapper.SysSubsystemMapper;
import com.andy.model.dto.SubsystemDTO;
import com.andy.model.vo.SubsystemVO;
import com.andy.service.SysSubsystemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SysSubsystemServiceImpl extends ServiceImpl<SysSubsystemMapper, SysSubsystem> implements SysSubsystemService {

    private final SysSubsystemMapper sysSubsystemMapper;

    @Override
    public List<SubsystemVO> getSubsystemVOList() {
        LambdaQueryWrapper<SysSubsystem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSubsystem::getDeleted, 0);
        wrapper.orderByAsc(SysSubsystem::getSort);
        List<SysSubsystem> list = this.list(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public List<SubsystemVO> getValidSubsystemVOList() {
        LambdaQueryWrapper<SysSubsystem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSubsystem::getStatus, 1);
        wrapper.eq(SysSubsystem::getDeleted, 0);
        wrapper.orderByAsc(SysSubsystem::getSort);
        List<SysSubsystem> list = this.list(wrapper);
        return list.stream().map(this::convertToVO).collect(Collectors.toList());
    }

    @Override
    public SubsystemVO getSubsystemVOById(Long id) {
        SysSubsystem subsystem = this.getById(id);
        if (subsystem == null) {
            return null;
        }
        return convertToVO(subsystem);
    }

    @Override
    public boolean saveSubsystem(SubsystemDTO subsystemDTO) {
        SysSubsystem subsystem = new SysSubsystem();
        BeanUtils.copyProperties(subsystemDTO, subsystem);
        if (subsystem.getSort() == null) {
            subsystem.setSort(0);
        }
        if (subsystem.getStatus() == null) {
            subsystem.setStatus(1);
        }
        return this.save(subsystem);
    }

    @Override
    public boolean updateSubsystem(SubsystemDTO subsystemDTO) {
        SysSubsystem subsystem = new SysSubsystem();
        BeanUtils.copyProperties(subsystemDTO, subsystem);
        return this.updateById(subsystem);
    }

    @Override
    public List<String> getAllCodes() {
        return sysSubsystemMapper.selectAllCodes();
    }

    @Override
    public boolean isValidCode(String code) {
        if (code == null || code.isEmpty()) {
            return true;
        }
        return sysSubsystemMapper.countByCode(code) > 0;
    }

    private SubsystemVO convertToVO(SysSubsystem subsystem) {
        SubsystemVO vo = new SubsystemVO();
        BeanUtils.copyProperties(subsystem, vo);
        return vo;
    }
}
