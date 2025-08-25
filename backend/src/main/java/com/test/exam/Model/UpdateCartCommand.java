package com.test.exam.Model;

public class UpdateCartCommand {
    private Integer cartItemId;
    private Integer quantity;

    public UpdateCartCommand(Integer cartItemId, Integer quantity){
        this.cartItemId = cartItemId;
        this.quantity = quantity;
    }

    //Getter for cartItemId
    public Integer getCartItemId(){
        return cartItemId;
    }

    //Setter for cartItemId
    public void setCartItemId(Integer cartItemId){
        this.cartItemId = cartItemId;
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
