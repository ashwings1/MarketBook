package com.test.exam.Model;

public class ProductDTO {
    private Integer id;
    private String name;
    private String description;
    private Double price;

    public ProductDTO(){

    }

    public ProductDTO(Product product){
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
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


    
}
