package com.test.exam.Security.JWT;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
//Conditional component - only active when Redis enabled
//Allows for env specific config (dev vs prod)
@ConditionalOnProperty(name = "app.redis.enabled", havingValue = "true", matchIfMissing = true)
public class TokenBlacklistService implements TokenBlacklist{

    private static final Logger logger = LoggerFactory.getLogger(TokenBlacklistService.class);

    private final RedisTemplate<String, String> redisTemplate;
    private final JwtUtil jwtUtil;

    //Constructor with RedisTemplate
    //@Lazy annotation prevents circular dependency between JwtUtil and TokenBlacklistService
    public TokenBlacklistService(RedisTemplate<String, String> redisTemplate, @Lazy JwtUtil jwtUtil){
        this.redisTemplate = redisTemplate;
        this.jwtUtil = jwtUtil;
    }

    //Blacklist token for duration of remaining validity
    //Only blacklists for remaining validity period - automatically expires from Redis
    //Prevents memory leaks and DB growth from accumulating expired tokens
    @Override
    public void blacklistToken(String token){
        try {
            //Get token expiration time
            Date expiration = jwtUtil.getClaims(token).getExpiration();
            long timeToLive = Math.max(0, expiration.getTime() - System.currentTimeMillis());
            blacklistToken(token, timeToLive);
        } catch (Exception e){
            //If token already expired, don't blacklist
            logger.warn("Failed to blacklist token: {}", e.getMessage());
        }
    }

    //Blacklist token for specific time to live
    //Uses hash for storage to prevent token leakage in Redis
    //Leverage Redis TTL for automatic cleanup
    @Override
    public void blacklistToken(String token, long timeToLive){
        //Using hash of token to prevent raw tokens from being stored even if Redis compromised
        String hashToken = DigestUtils.sha256Hex(token);
        redisTemplate.opsForValue().set("blacklisted:" + hashToken, "", timeToLive, TimeUnit.MILLISECONDS);
        logger.debug("Token blacklisted for {} ms", timeToLive);
    }

    //Check if token blacklisted
    @Override
    public boolean isTokenBlacklisted(String token){
        String hashToken = DigestUtils.sha256Hex(token);
        return Boolean.TRUE.equals(redisTemplate.hasKey("blacklisted:" + hashToken));
    }
    
}
