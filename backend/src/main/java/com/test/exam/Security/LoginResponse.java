package com.test.exam.Security;

public class LoginResponse {
    private String message;
    private boolean success;

    public LoginResponse(String message, boolean success){
        this.message = message;
        this.success = success;
    }

    //Getter for message
    public String getMessage(){
        return message;
    }

    //Setter for message
    public void setMessage(String message){
        this.message = message;
    }

    //Getter for success
    public boolean isSuccess(){
        return success;
    }

    //Setter for success
    public void setSuccess(boolean success){
        this.success = success;
    }
}
