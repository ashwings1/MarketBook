package com.test.exam.Model;

public class UpdateProductCommand {
    private Integer id;
    private Product product;

    public UpdateProductCommand(Integer id, Product product){
        this.id = id;
        this.product = product;
    }

    //Getter for id
    public Integer getId(){
        return id;
    }

    //Setter for id
    public void setId(Integer id){
        this.id = id;
    }

    //Getter for product
    public Product getProduct(){
        return product;
    }

    //Setter for product
    public void setProduct(Product product){
        this.product = product;
    }
    
}
