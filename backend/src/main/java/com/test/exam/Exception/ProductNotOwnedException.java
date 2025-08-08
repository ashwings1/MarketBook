package com.test.exam.Exception;

public class ProductNotOwnedException extends RuntimeException{
    public ProductNotOwnedException(){
        super("Product does not belong to the authenticated seller");
    }

    public ProductNotOwnedException(String message){
        super(message);
    }
}
