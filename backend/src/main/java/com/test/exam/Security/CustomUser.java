package com.test.exam.Security;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="customuser")
public class CustomUser {

    @Id
    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    //Default constructor
    public CustomUser(){

    }

    public CustomUser(String username, String password){
        this.username = username;
        this.password = password;
    }

    //Getter for username
    public String getUsername(){
        return username;
    }

    //Setter for username
    public void setUsername(String username){
        this.username = username;
    }

    //Getter for password
    public String getPassword(){
        return password;
    }

    //Setter for password
    public void setPassword(String password){
        this.password = password;
    }
}
 