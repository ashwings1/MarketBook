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
import com.test.exam.Model.UpdateAccountRequest;
import com.test.exam.Security.JWT.JwtUtil;

import io.jsonwebtoken.JwtException;

@Service
public class UpdateAccountService implements Command<UpdateAccountRequest, AccountDTO>{

    private final Logger logger = LoggerFactory.getLogger(UpdateAccountService.class);

    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final CustomUserRepository customUserRepository;

    public UpdateAccountService(UserDetailsService userDetailsService, JwtUtil jwtUtil, CustomUserRepository customUserRepository){
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.customUserRepository = customUserRepository;
    }

    @Override
    public ResponseEntity<AccountDTO> execute(UpdateAccountRequest request){

        String authHeader = request.getAuthHeader();
        CustomUser updateRequest = request.getUpdateData();

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

            //Update user first name and/or last name
            CustomUser user = customUserRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));
           
            if (updateRequest.getFirstName() != null){
                user.setFirstName(updateRequest.getFirstName());
            }

            if (updateRequest.getLastName() != null){
                user.setLastName(updateRequest.getLastName());
            }
            
            CustomUser savedUser = customUserRepository.save(user);
            
            AccountDTO accountDTO = new AccountDTO(savedUser);

            return ResponseEntity.ok(accountDTO);

        } catch (JwtException e){
            logger.debug("Invalid token in account retrieval: {}", e.getMessage());
            throw new UnauthorizedException("Invalid authentication token");
        }
 
    }
    
}
