package com.andy.service;

import com.andy.dto.UserBindRoleDTO;
import com.andy.dto.UserCreateDTO;
import com.andy.dto.UserQueryDTO;
import com.andy.dto.UserUpdateDTO;
import com.andy.entity.SysUser;
import com.andy.vo.PermissionTreeVO;
import com.andy.vo.UserVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    Long createUser(UserCreateDTO dto);

    void updateUser(UserUpdateDTO dto);

    void deleteUser(Long id);

    UserVO getUserById(Long id);

    Page<UserVO> getUserPage(Page<SysUser> page, UserQueryDTO dto);

    void bindRoles(UserBindRoleDTO dto);

    List<Long> getRoleIdsByUserId(Long userId);

    List<PermissionTreeVO> getUserPermissionTree(Long userId);
}
