package com.test.exam.Security;

public class LoginRequest {
    private String username;
    private String password;

    public LoginRequest(){}

    public LoginRequest(String username, String password){
        this.username = username;
        this.password = password;
    }

    //Getter for username
    public String getUsername(){
        return username;
    }

    //Setter for username
    public void setUsername(String username){
        this.username = username;
    }

    //Getter for password
    public String getPassword(){
        return password;
    }

    //Setter for password
    public void setPassword(String password){
        this.password = password;
    }
    
}
