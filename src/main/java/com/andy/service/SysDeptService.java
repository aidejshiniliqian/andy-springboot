package com.andy.service;

import com.andy.entity.system.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysDeptService extends IService<SysDept> {

    List<SysDept> getDeptsByUserId(Long userId);

    List<SysDept> getDeptTree();
}
