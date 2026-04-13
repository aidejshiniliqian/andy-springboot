package com.andy.service.impl;

import cn.hutool.crypto.digest.BCrypt;
import com.andy.entity.system.SysUser;
import com.andy.mapper.SysUserMapper;
import com.andy.model.dto.LoginDTO;
import com.andy.model.vo.LoginVO;
import com.andy.model.vo.PermissionVO;
import com.andy.service.AuthService;
import com.andy.service.SysPermissionService;
import com.andy.service.SysUserService;
import com.andy.service.TokenService;
import com.andy.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserService sysUserService;
    private final SysPermissionService sysPermissionService;
    private final SysUserMapper sysUserMapper;
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        SysUser user = sysUserService.getByUsername(loginDTO.getUsername());
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new RuntimeException("用户已被禁用");
        }

        if (!BCrypt.checkpw(loginDTO.getPassword(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        LoginVO loginVO = new LoginVO();
        BeanUtils.copyProperties(user, loginVO);
        loginVO.setUserId(user.getId());

        List<String> roles = sysUserService.getRoleCodesByUserId(user.getId());
        loginVO.setRoles(roles);

        List<String> permissions = sysUserService.getPermissionCodesByUserId(user.getId());
        loginVO.setPermissions(permissions);

        List<PermissionVO> menus = sysUserService.getUserMenuTree(user.getId(), loginDTO.getSubsystemCode());
        loginVO.setMenus(menus);

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        loginVO.setAccessToken(token);
        loginVO.setTokenType("Bearer");
        loginVO.setExpiresIn(jwtUtil.getExpirationTime());

        return loginVO;
    }

    @Override
    public void logout(String token) {
        if (token != null && !token.isEmpty()) {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            long expirationTime = jwtUtil.getExpirationTime();
            tokenService.blacklistToken(token, expirationTime);
        }
    }
}
