package com.andy.service.impl;

import com.andy.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private final StringRedisTemplate redisTemplate;
    private final ConcurrentHashMap<String, Long> tokenBlacklistCache;
    private final ScheduledExecutorService scheduler;
    private final boolean redisAvailable;

    private static final String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";

    @Autowired
    public TokenServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.tokenBlacklistCache = new ConcurrentHashMap<>();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();
        
        boolean available = false;
        try {
            redisTemplate.getConnectionFactory().getConnection().ping();
            available = true;
            log.info("Redis连接成功，使用Redis存储Token黑名单");
        } catch (Exception e) {
            log.warn("Redis连接失败，使用内存存储Token黑名单: {}", e.getMessage());
        }
        this.redisAvailable = available;
        
        // 启动定时清理任务，每10分钟清理一次过期的token
        scheduler.scheduleAtFixedRate(this::cleanupExpiredTokens, 10, 10, TimeUnit.MINUTES);
    }

    @Override
    public void blacklistToken(String token, long expirationTime) {
        long expireAt = System.currentTimeMillis() + expirationTime * 1000;
        
        if (redisAvailable) {
            try {
                String key = TOKEN_BLACKLIST_PREFIX + token;
                redisTemplate.opsForValue().set(key, "1", expirationTime, TimeUnit.SECONDS);
                log.debug("Token已加入Redis黑名单: {}", token.substring(0, 20) + "...");
            } catch (Exception e) {
                log.warn("Redis操作失败，降级到内存存储: {}", e.getMessage());
                tokenBlacklistCache.put(token, expireAt);
            }
        } else {
            tokenBlacklistCache.put(token, expireAt);
            log.debug("Token已加入内存黑名单: {}", token.substring(0, 20) + "...");
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token) {
        if (redisAvailable) {
            try {
                String key = TOKEN_BLACKLIST_PREFIX + token;
                return Boolean.TRUE.equals(redisTemplate.hasKey(key));
            } catch (Exception e) {
                log.warn("Redis查询失败，降级到内存查询: {}", e.getMessage());
                return checkMemoryBlacklist(token);
            }
        } else {
            return checkMemoryBlacklist(token);
        }
    }

    private boolean checkMemoryBlacklist(String token) {
        Long expireAt = tokenBlacklistCache.get(token);
        if (expireAt == null) {
            return false;
        }
        if (System.currentTimeMillis() > expireAt) {
            tokenBlacklistCache.remove(token);
            return false;
        }
        return true;
    }

    @Override
    public void refreshToken(String oldToken, String newToken, long expirationTime) {
        blacklistToken(oldToken, expirationTime);
    }

    private void cleanupExpiredTokens() {
        long now = System.currentTimeMillis();
        Set<String> expiredTokens = tokenBlacklistCache.entrySet().stream()
                .filter(entry -> entry.getValue() < now)
                .map(entry -> entry.getKey())
                .collect(java.util.stream.Collectors.toSet());
        
        expiredTokens.forEach(tokenBlacklistCache::remove);
        
        if (!expiredTokens.isEmpty()) {
            log.info("清理了 {} 个过期的Token黑名单记录", expiredTokens.size());
        }
    }
}
