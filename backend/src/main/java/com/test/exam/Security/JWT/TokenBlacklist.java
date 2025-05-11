package com.test.exam.Security.JWT;

public interface TokenBlacklist {
    //Using interfact to allow different implementations (Redis vs in-memory)
    //Following dependency inversion principle for better testability and flexibility

    //Blacklist token for duration of remaining validity
    void blacklistToken(String token);

    //Blacklist token for specific time to live
    void blacklistToken(String token, long timeToLive);

    //Check if token blacklisted
    boolean isTokenBlacklisted(String token);
}
