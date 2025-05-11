package com.test.exam.Security.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.test.exam.Exception.UnauthorizedException;
import com.test.exam.Security.AuthResponse;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;

@Service
public class TokenService {

    //Centralized service to manage tokens, separates token logic from Authentication Controller

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);

    private final JwtUtil jwtUtil;
    private final TokenBlacklist tokenBlacklist;
    private final UserDetailsService userDetailsService;

    public TokenService(JwtUtil jwtUtil, TokenBlacklist tokenBlacklist, UserDetailsService userDetailsService){
        this.jwtUtil = jwtUtil;
        this.tokenBlacklist = tokenBlacklist;
        this.userDetailsService = userDetailsService;
    }

    //Generates authentication tokens for user
    //Returns both access (short lifetime for security) and refresh (longer lifetime for improved UX) tokens
    public AuthResponse generateTokens(UserDetails userDetails){
        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        Date accessTokenExpiration = jwtUtil.getClaims(accessToken).getExpiration();
        Date refreshTokenExpiration = jwtUtil.getClaims(refreshToken).getExpiration();

        return new AuthResponse(accessToken, refreshToken, accessTokenExpiration, refreshTokenExpiration, "Bearer");
    }

    //Refreshes access token, blacklists old refresh token to prevent resuse attacks
    public AuthResponse refreshAccessToken(String refreshToken){
        try {
            String username = jwtUtil.extractUsername(refreshToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (!jwtUtil.isTokenValid(refreshToken, userDetails)){
                throw new UnauthorizedException("Invalid refresh token");
            }

            //Generate new tokens
            AuthResponse response = generateTokens(userDetails);

            //Blacklist old refresh token
            tokenBlacklist.blacklistToken(refreshToken);

            logger.debug("Token refreshed for user: {}", username);
            return response;

        } catch (JwtException e){
            logger.debug("Invalid refresh token: {}", e.getMessage());
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    //Validate token and extract user info, (remaining time, roles) without extra API calls
    public Map<String, Object> validateToken(String token){
        Map<String, Object> result = new HashMap<>();

        try {
            String username = jwtUtil.extractUsername(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            boolean isValid = jwtUtil.isTokenValid(token, userDetails);
            result.put("valid", isValid);

            if (isValid){
                result.put("username", username);

                Claims claims = jwtUtil.getClaims(token);
                if (claims.containsKey("roles")){
                    result.put("roles", claims.get("roles"));
                }

                //Calculate remaining time allows clients to proactively refresh tokens before they expire
                Date expiration = claims.getExpiration();
                long remainingTime = expiration.getTime() - System.currentTimeMillis();
                result.put("expiresIn", remainingTime / 1000); //seconds
                result.put("expiration", expiration);
            }

            return result;
        } catch (Exception e) {
            result.put("valid", false);
            return result;
        }
    }

    //Logout and invalidate tokens
    public void logout(String accessToken, String refreshToken){
        //Blacklist both tokens to completely terminate session
        if (StringUtils.hasText(accessToken)){
            tokenBlacklist.blacklistToken(accessToken);
        }

        if (StringUtils.hasText(refreshToken)){
            tokenBlacklist.blacklistToken(refreshToken);
        }

        SecurityContextHolder.clearContext();
    }
    
}
