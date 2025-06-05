package com.test.exam.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="customuser")
public class CustomUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    //Default constructor
    public CustomUser(){

    }

    //With id
    public CustomUser(Long id, String username, String password, String firstName, String lastName){
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Without id
    public CustomUser(String username, String password, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    //Getter for id
    public Long getId(){
        return id;
    }

    //Setter for id
    public void setId(Long id){
        this.id = id;
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
 