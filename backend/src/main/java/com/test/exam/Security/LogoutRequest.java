package com.test.exam.Security;

public class LogoutRequest {
    private String refreshToken;

    public LogoutRequest(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public LogoutRequest(){

    }

    //Getter for refreshToken
    public String getRefreshToken(){
        return refreshToken;
    }

    //Setter for refreshToken
    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }
}
