package com.andy.service;

import com.andy.model.dto.LoginDTO;
import com.andy.model.vo.LoginVO;

public interface AuthService {

    LoginVO login(LoginDTO loginDTO);

    void logout(String token);
}
