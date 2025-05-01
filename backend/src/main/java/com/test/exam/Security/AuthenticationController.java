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
