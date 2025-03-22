package com.example.todoapp.database;

import java.io.Serializable;

public class UserInfo extends Uid implements Serializable {
    private String email;
    private String lastName;
    private String firstName;

    public UserInfo(){

    }

    public String getEmail() {
        return email;
    }

    public UserInfo setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public UserInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }
}
