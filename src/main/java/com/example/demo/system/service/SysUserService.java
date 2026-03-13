package com.example.demo.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.system.dto.SysUserDTO;
import com.example.demo.system.entity.SysUser;
import com.example.demo.system.vo.SysMenuVO;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    void saveUser(SysUserDTO userDTO);

    void updateUser(SysUserDTO userDTO);

    void deleteUser(Long id);

    void bindRoles(Long userId, List<Long> roleIds);

    List<Long> getRoleIdsByUserId(Long userId);

    List<SysMenuVO> getUserMenuTree(Long userId);
}
