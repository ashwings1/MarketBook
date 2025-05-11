package com.test.exam.Model;

public class AccountDTO {
    private Long id;

    public AccountDTO(){}

    public AccountDTO(Long id){
        this.id = id;
    }

    //Getter for Id
    public Long getId(){
        return id;
    }

    //Setter for Id
    public void setId(Long id){
        this.id = id;
    }
    
}
