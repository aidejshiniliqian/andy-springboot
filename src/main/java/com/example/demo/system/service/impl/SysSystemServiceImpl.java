package com.example.demo.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.system.dto.SysSystemDTO;
import com.example.demo.system.entity.SysSystem;
import com.example.demo.system.mapper.SysSystemMapper;
import com.example.demo.system.service.SysSystemService;
import com.example.demo.system.vo.SysSystemVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysSystemServiceImpl extends ServiceImpl<SysSystemMapper, SysSystem> implements SysSystemService {

    @Override
    public List<SysSystemVO> listAll() {
        LambdaQueryWrapper<SysSystem> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByAsc(SysSystem::getSort, SysSystem::getId);
        List<SysSystem> systems = this.list(wrapper);
        return systems.stream()
                .map(system -> BeanUtil.copyProperties(system, SysSystemVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<SysSystemVO> listActive() {
        LambdaQueryWrapper<SysSystem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSystem::getStatus, 1);
        wrapper.orderByAsc(SysSystem::getSort, SysSystem::getId);
        List<SysSystem> systems = this.list(wrapper);
        return systems.stream()
                .map(system -> BeanUtil.copyProperties(system, SysSystemVO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void saveSystem(SysSystemDTO systemDTO) {
        SysSystem system = BeanUtil.copyProperties(systemDTO, SysSystem.class);
        this.save(system);
    }

    @Override
    public void updateSystem(SysSystemDTO systemDTO) {
        SysSystem system = BeanUtil.copyProperties(systemDTO, SysSystem.class);
        this.updateById(system);
    }

    @Override
    public void deleteSystem(Long id) {
        this.removeById(id);
    }

    @Override
    public SysSystemVO getByCode(String systemCode) {
        LambdaQueryWrapper<SysSystem> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysSystem::getSystemCode, systemCode);
        SysSystem system = this.getOne(wrapper);
        return system != null ? BeanUtil.copyProperties(system, SysSystemVO.class) : null;
    }
}
