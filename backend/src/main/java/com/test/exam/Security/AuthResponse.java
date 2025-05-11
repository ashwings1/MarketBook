package com.test.exam.Security;

import java.util.Date;

public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Date accessTokenExpiration;
    private Date refreshTokenExpiration;
    private String tokenType;

    public AuthResponse(String accessToken, String refreshToken, Date accessTokenExpiration, Date refreshTokenExpiration, String tokenType){
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiration = accessTokenExpiration;
        this.refreshTokenExpiration = refreshTokenExpiration;
        this.tokenType = tokenType;
    }

    public AuthResponse(){

    }

    //Getter for accessToken
    public String getAccessToken(){
        return accessToken;
    }

    //Setter for accessToken
    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    //Getter for refreshToken
    public String getRefreshToken(){
        return refreshToken;
    }

    //Setter for refreshToken
    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    //Getter for accessTokenExpiration
    public Date getAccessTokenExpiration(){
        return accessTokenExpiration;
    }

    //Setter for accessTokenExpiration
    public void setAccessTokenExpiration(Date accessTokenExpiration){
        this.accessTokenExpiration = accessTokenExpiration;
    }

    //Getter for refreshTokenExpiration
    public Date getRefreshTokenExpiration(){
        return refreshTokenExpiration;
    }

    //Setter for refreshTokenExpiration
    public void setRefreshTokenExpiration(Date refreshTokenExpiration){
        this.refreshTokenExpiration = refreshTokenExpiration;
    }

    //Getter for tokenType
    public String getTokenType(){
        return tokenType;
    }

    //Setter for tokenType
    public void setTokenType(String tokenType){
        this.tokenType = tokenType;
    }


    
}
