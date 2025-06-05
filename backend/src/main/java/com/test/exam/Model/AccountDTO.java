package com.test.exam.Model;

public class AccountDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public AccountDTO(){}

    public AccountDTO(Long id){
        this.id = id;
    }

    public AccountDTO(CustomUser user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    //Getter for Id
    public Long getId(){
        return id;
    }

    //Setter for Id
    public void setId(Long id){
        this.id = id;
    }

    //Getter for firstName
    public String getFirstName(){
        return firstName;
    }

    //Setter for firstName
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    //Getter for lastName
    public String getLastName(){
        return lastName;
    }

    //Setter for lastName
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    
}
