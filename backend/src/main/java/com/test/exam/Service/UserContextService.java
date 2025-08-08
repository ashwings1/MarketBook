package com.test.exam.Service;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.test.exam.Exception.UnauthorizedException;
import com.test.exam.Model.CustomUser;
import com.test.exam.Model.CustomUserRepository;

@Service
public class UserContextService {
    
    private final CustomUserRepository customUserRepository;

    public UserContextService(CustomUserRepository customUserRepository){
        this.customUserRepository = customUserRepository;
    }

    public Integer getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated()){
            throw new UnauthorizedException("User not authenticated");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<CustomUser> userOptional = customUserRepository.findByUsername(username);
        
        if (userOptional.isEmpty()){
            throw new UnauthorizedException("User not found");
        }

        CustomUser user = userOptional.get();
        return user.getId().intValue();
    }

    public CustomUser getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()){
            throw new UnauthorizedException("User not authenticated");
        }
    
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        return customUserRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User not found"));
        
    }
}
