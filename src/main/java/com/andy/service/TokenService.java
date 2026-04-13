package com.andy.service;

public interface TokenService {

    void blacklistToken(String token, long expirationTime);

    boolean isTokenBlacklisted(String token);

    void refreshToken(String oldToken, String newToken, long expirationTime);
}
