package com.andy;

import com.andy.util.JwtUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JwtUtilTest {

    @Test
    public void testGenerateToken() {
        String token = JwtUtil.generateToken(1L, "admin");
        System.out.println("生成的Token: " + token);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    public void testGetUsernameFromToken() {
        String token = JwtUtil.generateToken(1L, "admin");
        String username = JwtUtil.getUsernameFromToken(token);
        assertEquals("admin", username);
    }

    @Test
    public void testGetUserIdFromToken() {
        String token = JwtUtil.generateToken(100L, "testuser");
        Long userId = JwtUtil.getUserIdFromToken(token);
        assertEquals(100L, userId);
    }

    @Test
    public void testValidateToken() {
        String token = JwtUtil.generateToken(1L, "admin");
        boolean isValid = JwtUtil.validateToken(token);
        assertTrue(isValid);
    }

    @Test
    public void testInvalidateToken() {
        String token = JwtUtil.generateToken(1L, "admin");
        assertTrue(JwtUtil.validateToken(token));

        JwtUtil.invalidateToken(token);
        assertFalse(JwtUtil.validateToken(token));
    }

    @Test
    public void testValidateInvalidToken() {
        boolean isValid = JwtUtil.validateToken("invalid.token.here");
        assertFalse(isValid);
    }
}
