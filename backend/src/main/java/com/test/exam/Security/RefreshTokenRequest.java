package com.test.exam.Security;

public class RefreshTokenRequest {
    private String refreshToken;

    public RefreshTokenRequest(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public RefreshTokenRequest(){

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
