package com.test.exam.Model;

public class ErrorResponse {

    private String message;

    public ErrorResponse(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
    
}
