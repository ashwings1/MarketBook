package com.test.exam.Exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {
    
    public ProductNotFoundException(){
        super(ErrorMessages.PRODUCT_NOT_FOUND.getMessage());
    }
    
}
