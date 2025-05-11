package com.test.exam.Security.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    //Secret key used for signing - should be at least 256 bits (32 bytes) for HS256
    //Production -> should be stored securely (environment variable or vault)
    @Value("${jwt.secret:yoursecretkeyanditmustbelongeneoughforsecurity}")
    private String secret;

    //Short lifetime for access tokens reduces risk window if compromised
    @Value("${jwt.access-token.expiration:300000}") //5 minutes default
    private Long accessTokenExpiration;

    //Long lifetime for access tokens improves UX while maintaining security through token rotation pattern (old refresh tokens blacklisted)
    @Value("${jwt.refresh-token.expiration:600000}") //10 Minutes default
    private Long refreshTokenExpiration;

    @Autowired
    private TokenBlacklist tokenBlacklistService;

    public String generateAccessToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        //Includes roles in token to avoid database lookups on each request
        //Enables stateless authorization decisions
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        
        return createToken(claims, userDetails.getUsername(), accessTokenExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails){
        //Refresh tokens intentionally contain minimal claims to reduce sensitive data exposure
        return createToken(new HashMap<>(), userDetails.getUsername(), refreshTokenExpiration);
    }

    private String createToken(Map<String, Object> claims, String subject, long expiration){
        return Jwts
                .builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey(){
        //Base64 decoding allows for longer, more complex secrets in config
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims getClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isExpired(token) && !tokenBlacklistService.isTokenBlacklisted(token);
    }

    public boolean isExpired(String token){
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }

    /* 
    public static String generateToken(User user){
        return Jwts
                .builder()
                .subject(user.getUsername())
                .expiration(new Date(System.currentTimeMillis() + 300_000)) //5 Minutes
                .signWith(getSigninKey())
                .compact();
    }

    private static SecretKey getSigninKey(){
        byte[] keyBytes = Decoders.BASE64.decode("yoursecretkeyanditmustbelongeneoughforsecurity");
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static Claims getClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean isTokenValid(String token){
        //Can add more validation here
        return !isExpired(token);
    }

    private static boolean isExpired(String token){
        return getClaims(token)
                .getExpiration()
                .before(new Date());
    }
    */
    
}
