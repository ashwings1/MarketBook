package com.test.exam.Model;

public class UpdateAccountRequest {
    private String authHeader;
    private CustomUser updateData;

    public UpdateAccountRequest(){};

    public UpdateAccountRequest(String authHeader, CustomUser updateData){
        this.authHeader = authHeader;
        this.updateData = updateData;
    }

    //Getter for authHeader
    public String getAuthHeader(){
        return authHeader;
    }

    //Setter for authHeader
    public void setAuthHeader(String authHeader){
        this.authHeader = authHeader;
    }

    //Getter for updateData
    public CustomUser getUpdateData(){
        return updateData;
    }

    //Setter for updateData
    public void setUpdateData(CustomUser updateData){
        this.updateData = updateData;
    }
    
}
