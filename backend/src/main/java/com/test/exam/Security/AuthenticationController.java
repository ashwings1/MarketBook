package com.test.exam.Security;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;


@RestController
public class AuthenticationController {
    
    private final AuthenticationManager authenticationManager;
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserRepository customUserRepository, PasswordEncoder passwordEncoder){
        this.authenticationManager = authenticationManager;
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
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

    @PostMapping("/reset-password")
    public ResponseEntity<LoginResponse> resetPassword(@Valid @RequestBody ResetPassword request, BindingResult bindingResult){
        try {
            //Checking for validation errors
            if (bindingResult.hasErrors()){
                String errorMessage = bindingResult.getFieldError().getDefaultMessage();
                return ResponseEntity.status(401).body(new LoginResponse(errorMessage, false));
            }

            Optional<CustomUser> optionalUser = customUserRepository.findByUsername(request.getUsername());

            if (optionalUser.isEmpty()){
                return ResponseEntity.status(401).body(new LoginResponse("User not found", false));
            } 

            CustomUser user = optionalUser.get();
            String updatedPassword = passwordEncoder.encode(request.getNewPassword());
            user.setPassword(updatedPassword);
            customUserRepository.save(user);

            return ResponseEntity.ok(new LoginResponse("Password reset successfully", true));
        } catch (AuthenticationException e){
            return ResponseEntity.status(401).body(new LoginResponse("Unable to reset password", false));
        }
    }

    

    /* 
    @PostMapping("/logout")
    public ResponseEntity<LoginResponse> logout(HttpServletRequest request){
        try{
            SecurityContext securityContext = SecurityContextHolder.getContext();

            if (securityContext.getAuthentication() != null){
                //Clear Security Context
                SecurityContextHolder.clearContext();

                //Invalidate session
                HttpSession session = request.getSession(false);
                if (session != null){
                    session.invalidate();
                }
                
                return ResponseEntity.ok(new LoginResponse("Logout successful", true));
            } else {
                return ResponseEntity.status(401).body(new LoginResponse("No active session", false));
            }

        } catch (AuthenticationException e){
            return ResponseEntity.status(500).body(new LoginResponse("Error logging out" + e.getMessage(), false));
        }
    }
        */
}
