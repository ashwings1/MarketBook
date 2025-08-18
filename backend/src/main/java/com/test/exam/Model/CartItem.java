package com.test.exam.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "cartitem")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cart_id")
    private Integer cartId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private Integer quantity;

    //Default constructor
    public CartItem(){

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

   
}

