package com.test.exam.Model;

public class CartSummaryDTO {
    private Integer totalItems;
    private Double totalAmount;
    private Integer uniqueItems;

    public CartSummaryDTO(){}

    public CartSummaryDTO(Integer totalItems, Double totalAmount, Integer uniqueItems){
        this.totalItems = totalItems;
        this.totalAmount = totalAmount;
        this.uniqueItems = uniqueItems;
    }

    //Getter for totalItems
    public Integer getTotalItems(){
        return totalItems;
    }

    //Setter for totalItems
    public void setTotalItems(Integer totalItems){
        this.totalItems = totalItems;
    }

    //Getter for totalAmount
    public Double getTotalAmount(){
        return totalAmount;
    }

    //Setter for totalAmount
    public void setTotalAmount(Double totalAmount){
        this.totalAmount = totalAmount;
    }

    //Getter for uniqueItems
    public Integer getUniqueItems(){
        return uniqueItems;
    }

    //Setter for uniqueItems
    public void setUniqueItems(Integer uniqueItems){
        this.uniqueItems = uniqueItems;
    }
}
