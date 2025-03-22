package com.example.todoapp.database;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class Uid implements Serializable {
    private String uid;
    public Uid(){}

    public Uid setUid(String uid) {
        this.uid = uid;
        return this;
    }
    @Exclude
    public String getUid() {
        return uid;
    }
}
