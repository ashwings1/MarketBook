package com.test.exam.Security;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.exam.Exception.UnauthorizedException;
import com.test.exam.Model.CustomUser;
import com.test.exam.Model.CustomUserRepository;
import com.test.exam.Security.JWT.TokenService;
import com.test.exam.Service.CreateNewUserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    
    private final CreateNewUserService createNewUserService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final CustomUserRepository customUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationController(CreateNewUserService createNewUserService, AuthenticationManager authenticationManager, TokenService tokenService, CustomUserRepository customUserRepository, PasswordEncoder passwordEncoder){
        this.createNewUserService = createNewUserService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.customUserRepository = customUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createNewUser(@RequestBody CustomUser user){
        return createNewUserService.execute(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest loginRequest){
        try {
            //Authenticate user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), 
                        loginRequest.getPassword()
                    )
            );

            //Set authentication in security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Generate tokens using token service
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            AuthResponse authResponse = tokenService.generateTokens(userDetails);

            logger.debug("User logged in: {}", loginRequest.getUsername());

            //Return tokens
            return ResponseEntity.ok(authResponse);
        } catch (BadCredentialsException e){
            logger.debug("Login failed for user: {}", loginRequest.getUsername());
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(@RequestHeader(value = "Authorization", required = false) String authHeader, @RequestBody(required = false) LogoutRequest logoutRequest){
        String accessToken = null;
        String refreshToken = null;

        //Extract access token from header
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            accessToken = authHeader.substring(7);
        }

        //Extract refresh token from header
        if (logoutRequest != null){
            refreshToken = logoutRequest.getRefreshToken();
        }

        //If no tokens provided, return error
        if (accessToken == null && refreshToken == null){
            logger.debug("Logout attempt with no valid tokens");
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "No valid tokens provided"));
        }

        //Logout with token service
        tokenService.logout(accessToken, refreshToken);

        logger.debug("User logged out successfully");
        return ResponseEntity.ok(Collections.singletonMap("message", "Logout successful"));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest request){
        try {
            AuthResponse authResponse = tokenService.refreshAccessToken(request.getRefreshToken());
            return ResponseEntity.ok(authResponse);
        } catch (Exception e){
            logger.debug("Token refresh failed: {}", e.getMessage());
            throw new UnauthorizedException("Invalid refresh token");
        }
    }

    @PostMapping("/validate-token")
    public ResponseEntity<Map<String, Object>> validateToken(@RequestHeader("Authorization") String authHeader){
        if (authHeader != null && authHeader.startsWith("Bearer ")){
            String token = authHeader.substring(7);
            return ResponseEntity.ok(tokenService.validateToken(token));
        }
        return ResponseEntity.ok(Collections.singletonMap("valid", false));
    }


    @PostMapping("/reset-password")
    public ResponseEntity<LoginResponse> resetPassword(@Valid @RequestBody ResetPassword request, BindingResult bindingResult){
        try {
            //Checking for validation errors
            if (bindingResult.hasErrors()){
                String errorMessage = bindingResult.getFieldError().getDefaultMessage();
                return ResponseEntity.status(400).body(new LoginResponse(errorMessage, false));
            }

            Optional<CustomUser> optionalUser = customUserRepository.findByUsername(request.getUsername());
            
            if (optionalUser.isEmpty()){
                return ResponseEntity.status(404).body(new LoginResponse("User not found", false));
            } 

            CustomUser user = optionalUser.get();

            String updatedPassword = passwordEncoder.encode(request.getNewPassword());
            user.setPassword(updatedPassword);
            customUserRepository.save(user);

            return ResponseEntity.ok(new LoginResponse("Password reset successfully", true));
        } catch (AuthenticationException e){
            return ResponseEntity.status(500).body(new LoginResponse("Unable to reset password", false));
        }
    }

}
