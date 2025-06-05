package com.test.exam.Service;

import java.util.ArrayList;
import java.util.Optional;

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

        //Add roles and authorities to user -> use relational mappings to get roles and authorities

        return new User(
                customUser.getUsername(),
                customUser.getPassword(),
                new ArrayList<>()
        );
    }

}
