package com.test.exam.Model;

public class CartItemDTO {
    private Integer id;
    private Integer cartId;
    private Integer productId;
    private Integer quantity;
    private String productName;
    private Double productPrice;

    public CartItemDTO(){}

    public CartItemDTO(CartItem cartItem){
        this.id = cartItem.getId();
        this.cartId = cartItem.getCartId();
        this.productId = cartItem.getProductId();
        this.quantity = cartItem.getQuantity();
    }

    //Getter for ID
    public Integer getId(){
        return id;
    }

    //Setter for ID
    public void setId(Integer id){
        this.id = id;
    }

    //Getter for CartId
    public Integer getCartId(){
        return cartId;
    }

    //Setter for CartId
    public void setCartId(Integer cartId){
        this.cartId = cartId;
    }

    //Getter for ProductId
    public Integer getProductId(){
        return productId;
    }

    //Setter for ProductId
    public void setProductId(Integer productId){
        this.productId = productId;
    }

    //Getter for Quantity
    public Integer getQuantity(){
        return quantity;
    }

    //Setter for Quantity
    public void setQuantity(Integer quantity){
        this.quantity = quantity;
    }

    //Getter for ProductName
    public String getProductName(){
        return productName;
    }

    //Setter for ProductName
    public void setProductName(String productName){
        this.productName = productName;
    }

    //Getter for ProductPrice
    public Double getProductPrice(){
        return productPrice;
    }

    //Setter for ProductPrice
    public void setProductPrice(Double productPrice){
        this.productPrice = productPrice;
    }

}
