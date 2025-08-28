package com.test.exam.Model;

public class UpdateCartCommand {
    private Integer productId;
    private Integer quantity;

    public UpdateCartCommand(Integer productId, Integer quantity){
        this.productId = productId;
        this.quantity = quantity;
    }

    //Getter for productId
    public Integer getProductId(){
        return productId;
    }

    //Setter for productId
    public void setProductId(Integer productId){
        this.productId = productId;
    }

    //Getter for quantity
    public Integer getQuantity(){
        return quantity;
    }

    //Setter for quantity
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }
    
}
