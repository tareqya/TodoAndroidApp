package com.example.todoapp.auth;

public class UserAuth {
    private String email;
    private String password;

    public UserAuth() {

    }

    public String getEmail() {
        return email;
    }

    public UserAuth setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserAuth setPassword(String password) {
        this.password = password;
        return this;
    }
}
