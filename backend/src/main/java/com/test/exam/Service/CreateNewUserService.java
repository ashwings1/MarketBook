package com.test.exam.Service;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.test.exam.Command;
import com.test.exam.Security.CustomUser;
import com.test.exam.Security.CustomUserRepository;

@Service
public class CreateNewUserService implements Command<CustomUser, String>{

    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder encoder;

    public CreateNewUserService(CustomUserRepository customUserRepository, PasswordEncoder encoder){
        this.customUserRepository = customUserRepository;
        this.encoder = encoder;
    }

    @Override
    public ResponseEntity<String> execute(CustomUser user){

        Optional<CustomUser> optionalUser = customUserRepository.findByUsername(user.getUsername());
        if (!optionalUser.isPresent()){
            customUserRepository.save(new CustomUser(user.getUsername(), encoder.encode(user.getPassword())));
            return ResponseEntity.ok("Success");
        }
        
        return ResponseEntity.badRequest().body("Failure");
    }
}
