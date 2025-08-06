package com.test.exam.Model;

import java.time.LocalDateTime;

public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;
    private String category;
    private String imageUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer sellerId;


    public ProductDTO(){

    }

    public ProductDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.category = product.getCategory();
        this.imageUrl = product.getImageUrl();
        this.createdAt = product.getCreatedAt();
        this.updatedAt = product.getUpdatedAt();
        this.sellerId = product.getSellerId();
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
