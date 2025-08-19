package com.test.exam.Model;

import java.time.LocalDateTime;

public class CartDTO {
    private Integer id;
    private Integer userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CartDTO(){}

    public CartDTO(Cart cart){
        this.id = cart.getId();
        this.userId = cart.getUserId();
        this.createdAt = cart.getCreatedAt();
        this.updatedAt =cart.getUpdatedAt();
    }

    //Getter for ID
    public Integer getId(){
        return id;
    }

    //Setter for ID
    public void setId(Integer id){
        this.id = id;
    }

    //Getter for UserId
    public Integer getUserId(){
        return userId;
    }

    //Setter for UserId
    public void setUserId(Integer userId){
        this.userId = userId;
    }

   //Getter for created_at
    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    //Setter for created_at
    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    //Getter for updated_at
    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    //Setter for updated_at
    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }
    
}
