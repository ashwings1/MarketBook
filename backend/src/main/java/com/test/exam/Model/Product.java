package com.test.exam.Model;


import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "examproduct")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "category")
    private String category;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "seller_id", nullable = false)
    private Integer sellerId;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    //Default constructor
    public Product(){

    }

    //Getter for ID
    public Integer getId(){
        return id;
    }

    //Setter for ID
    public void setId(Integer id){
        this.id = id;
    }

    //Getter for name
    public String getName(){
        return name;
    }

    //Setter for name
    public void setName(String name){
        this.name = name;
    }

    //Getter for description
    public String getDescription(){
        return description;
    }

    //Setter for description
    public void setDescription(String description){
        this.description = description;
    }

    //Getter for price
    public Double getPrice(){
        return price;
    }

    //Setter for price
    public void setPrice(Double price){
        this.price = price;
    }

    //Getter for category
    public String getCategory(){
        return category;
    }

    //Setter for category
    public void setCategory(String category){
        this.category = category;
    }

    //Getter for image_url
    public String getImageUrl(){
        return imageUrl;
    }

    //Setter for image_url
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
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

    //Getter for seller_id
    public Integer getSellerId(){
        return sellerId;
    }

    //Setter for seller_id
    public void setSellerId(Integer sellerId){
        this.sellerId = sellerId;
    }
}
