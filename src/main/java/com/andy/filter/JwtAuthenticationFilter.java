package com.andy.filter;

import com.andy.common.CommonResult;
import com.andy.common.ResultCode;
import com.andy.service.TokenService;
import com.andy.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final TokenService tokenService;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        if (requestUri.startsWith("/auth/login") || requestUri.startsWith("/swagger") 
                || requestUri.startsWith("/v3/api-docs") || requestUri.startsWith("/webjars")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        token = token.substring(7);

        try {
            if (tokenService.isTokenBlacklisted(token)) {
                writeErrorResponse(response, ResultCode.TOKEN_INVALID, "Token已被注销");
                return;
            }

            if (jwtUtil.validateToken(token)) {
                Long userId = jwtUtil.getUserIdFromToken(token);
                String username = jwtUtil.getUsernameFromToken(token);
                request.setAttribute("userId", userId);
                request.setAttribute("username", username);
                filterChain.doFilter(request, response);
            } else {
                writeErrorResponse(response, ResultCode.TOKEN_INVALID, "Token无效");
            }
        } catch (ExpiredJwtException e) {
            writeErrorResponse(response, ResultCode.TOKEN_EXPIRED, "Token已过期");
        } catch (Exception e) {
            writeErrorResponse(response, ResultCode.TOKEN_INVALID, "Token解析失败");
        }
    }

    private void writeErrorResponse(HttpServletResponse response, ResultCode resultCode, String message) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        CommonResult<Void> result = CommonResult.error(resultCode.getCode(), message);
        response.getWriter().write(objectMapper.writeValueAsString(result));
    }
}
