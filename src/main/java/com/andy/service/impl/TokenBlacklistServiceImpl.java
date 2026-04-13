package com.andy.service.impl;

import com.andy.service.TokenBlacklistService;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class TokenBlacklistServiceImpl implements TokenBlacklistService {

    private final ConcurrentHashMap<String, Long> blacklist = new ConcurrentHashMap<>();

    private static final long BLACKLIST_TTL = 24 * 60 * 60 * 1000L;

    @Override
    public void addToBlacklist(String token) {
        blacklist.put(token, System.currentTimeMillis() + BLACKLIST_TTL);
    }

    @Override
    public boolean isBlacklisted(String token) {
        Long expireTime = blacklist.get(token);
        if (expireTime == null) {
            return false;
        }
        if (System.currentTimeMillis() > expireTime) {
            blacklist.remove(token);
            return false;
        }
        return true;
    }
}
