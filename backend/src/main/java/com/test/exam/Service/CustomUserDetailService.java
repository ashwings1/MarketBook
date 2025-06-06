package com.test.exam.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.test.exam.Model.CustomUser;
import com.test.exam.Model.CustomUserRepository;

@Service
public class CustomUserDetailService implements UserDetailsService{
    
    private final CustomUserRepository customUserRepository;

    public CustomUserDetailService(CustomUserRepository customUserRepository){
        this.customUserRepository = customUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<CustomUser> userOptional = customUserRepository.findByUsername(username);
        
        if(!userOptional.isPresent()){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        CustomUser customUser = userOptional.get();

        // Convert your Role enum to Spring Security authorities
        Collection<GrantedAuthority> authorities = Collections.singletonList(
            new SimpleGrantedAuthority("ROLE_" + customUser.getRole().name())
        );

        return User.builder()
                .username(customUser.getUsername())
                .password(customUser.getPassword())
                .authorities(authorities)
                .build();
    }
}


