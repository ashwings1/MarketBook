package com.test.exam.Security;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//DTO for ResetPassword
public class ResetPassword {
    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "New password cannot be empty")
    @Size(min = 1, message = "Password must be at least 1 characters")
    private String newPassword;

    public ResetPassword(){}

    public ResetPassword(String username, String newPassword){
        this.username = username;
        this.newPassword = newPassword;
    }

    //Getter for username
    public String getUsername(){
        return username;
    }

    //Setter for username
    public void setUsername(String username){
        this.username = username;
    }

    //Getter for newPassword
    public String getNewPassword(){
        return newPassword;
    }

    //Setter for newPassword
    public void setNewPassword(String newPassword){
        this.newPassword = newPassword;
    }
    
}
