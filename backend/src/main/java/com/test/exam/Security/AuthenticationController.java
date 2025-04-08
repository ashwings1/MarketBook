package com.test.exam.Security;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            if (authentication.isAuthenticated()){
                //Generate JWT token in future here
                //Simplicity -> returning only success message
                return ResponseEntity.ok(new LoginResponse("Login successful", true));
            } else {
                return ResponseEntity.status(401).body(new LoginResponse("Authentication failed", false));
            }
        } catch (AuthenticationException e){
            return ResponseEntity.status(401).body(new LoginResponse("Invalid username or password", false));
        }
    }
}
