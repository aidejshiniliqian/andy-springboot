package com.example.demo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.system.dto.SysSystemDTO;
import com.example.demo.system.entity.SysSystem;
import com.example.demo.system.vo.SysSystemVO;

import java.util.List;

public interface SysSystemService extends IService<SysSystem> {

    List<SysSystemVO> listAll();

    List<SysSystemVO> listActive();

    void saveSystem(SysSystemDTO systemDTO);

    void updateSystem(SysSystemDTO systemDTO);

    void deleteSystem(Long id);

    SysSystemVO getByCode(String systemCode);
}
