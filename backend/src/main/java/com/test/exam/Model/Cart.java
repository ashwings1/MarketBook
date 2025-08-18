package com.test.exam.Model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cart")
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //Default constructor
    public Cart(){

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
