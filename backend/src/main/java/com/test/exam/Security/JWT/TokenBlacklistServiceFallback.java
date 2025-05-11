package com.test.exam.Security.JWT;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
//Activated only if Redis disabled - for in-memory fallback
//Useful for local development without Redis dependency
@ConditionalOnProperty(name = "app.redis.enabled", havingValue = "false")
public class TokenBlacklistServiceFallback implements TokenBlacklist{
    
    private static final Logger logger = LoggerFactory.getLogger(TokenBlacklistServiceFallback.class);

    private final Map<String, Long> blacklistedTokens = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    @Autowired
    @Lazy //Prevents circular dependency with JwtUtil
    private JwtUtil jwtUtil;

    @PostConstruct
    public void init(){
        //Schedule cleanup to prevent memory leaks in long-running applications
        //Important for in-memory storage which doesn't have automatic expiration like Redis
        scheduler.scheduleAtFixedRate(this::cleanupExpiredTokens, 10, 10, TimeUnit.MINUTES);
        logger.info("In-memory token blacklist initialized");
    }

    @PreDestroy
    public void shutdown(){
        scheduler.shutdown();
    }

    private void cleanupExpiredTokens(){
        //Remove expired tokens to prevent memory leaks in long-running applications
        long currentTime = System.currentTimeMillis();
        blacklistedTokens.entrySet().removeIf(entry -> entry.getValue() <= currentTime);
        logger.debug("Cleaned up expired tokens. Remaining: {}", blacklistedTokens.size());
    }

    @Override
    public void blacklistToken(String token, long timeToLive){
        //Store token hash instead of actual token for security to prevent memory leakage
        String tokenHash = DigestUtils.sha256Hex(token);
        blacklistedTokens.put(tokenHash, System.currentTimeMillis() + timeToLive);
        logger.debug("Token blacklisted in memory for {} ms", timeToLive);
    }

    @Override
    public void blacklistToken(String token){
        try {
            //Extract expiration from token to determine blacklist duration
            //Mimics Redis TTL functionality in memory
            Claims claims = jwtUtil.getClaims(token);
            Date expiration = claims.getExpiration();
            long timeToLive = Math.max(0, expiration.getTime() - System.currentTimeMillis());
            blacklistToken(token, timeToLive);
        } catch (Exception e){
            //If token already expired, no need to blacklist
            logger.warn("Failed to blacklist token: {}", e.getMessage());
        }
    }

    @Override
    public boolean isTokenBlacklisted(String token){
        String tokenHash = DigestUtils.sha256Hex(token);
        Long expirationTime = blacklistedTokens.get(tokenHash);
        if (expirationTime == null){
            return false;
        }

        //Remove expired tokens on read - reduces memory usage
        if (expirationTime <= System.currentTimeMillis()){
            blacklistedTokens.remove(tokenHash);
            return false;
        }

        return true;
    }

}
