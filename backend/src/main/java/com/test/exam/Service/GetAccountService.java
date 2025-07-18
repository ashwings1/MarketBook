package com.test.exam.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Exception.ResourceNotFoundException;
import com.test.exam.Exception.UnauthorizedException;
import com.test.exam.Model.AccountDTO;
import com.test.exam.Model.CustomUser;
import com.test.exam.Model.CustomUserRepository;
import com.test.exam.Security.JWT.JwtUtil;

import io.jsonwebtoken.JwtException;

@Service
public class GetAccountService implements Command<String, AccountDTO>{

    private final Logger logger = LoggerFactory.getLogger(GetAccountService.class);
    
    private final CustomUserRepository customUserRepository;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public GetAccountService(CustomUserRepository customUserRepository, JwtUtil jwtUtil, UserDetailsService userDetailsService){
        this.customUserRepository = customUserRepository;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public ResponseEntity<AccountDTO> execute(String authHeader){

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new UnauthorizedException("Valid authentication token required");
        }

        //Extract token from header
        String token = authHeader.substring(7);

        try {
            //Extract username from token
            String username = jwtUtil.extractUsername(token);

            //Check if token valid
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!jwtUtil.isTokenValid(token, userDetails)){
                throw new UnauthorizedException("Invalid or expired token");
            }

            //Fetch complete user data from DB
            CustomUser user = customUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
            
            AccountDTO accountDTO = new AccountDTO(user);
            
            logger.debug("Account details retrieved for user: {}", username);
            return ResponseEntity.ok(accountDTO);
            
        } catch (JwtException e){
            logger.debug("Invalid token in account retrieval: {}", e.getMessage());
            throw new UnauthorizedException("Invalid authentication token");
        }
    }
    
}
