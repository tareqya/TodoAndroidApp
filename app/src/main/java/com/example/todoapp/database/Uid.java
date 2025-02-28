package com.example.todoapp.database;

import com.google.firebase.firestore.Exclude;

public class Uid {
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
