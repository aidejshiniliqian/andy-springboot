package com.andy.service;

import com.andy.entity.system.SysUser;
import com.andy.model.dto.UserDTO;
import com.andy.model.vo.PermissionVO;
import com.andy.model.vo.UserVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    List<String> getRoleCodesByUserId(Long userId);

    List<String> getPermissionCodesByUserId(Long userId);

    boolean assignRoles(Long userId, List<Long> roleIds);

    boolean bindRoles(Long userId, List<Long> roleIds);

    boolean unbindRoles(Long userId, List<Long> roleIds);

    UserVO getUserVOById(Long id);

    boolean saveUser(UserDTO userDTO);

    boolean updateUser(UserDTO userDTO);

    List<UserVO> getUserVOList();

    List<PermissionVO> getUserMenuTree(Long userId);

    List<PermissionVO> getUserMenuTree(Long userId, String subsystemCode);
}
