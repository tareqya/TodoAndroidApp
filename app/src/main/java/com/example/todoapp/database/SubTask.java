package com.example.todoapp.database;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class SubTask implements Serializable {
    private String title;
    private String status;

    public SubTask() {}

    public String getTitle() {
        return title;
    }

    public SubTask setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SubTask setStatus(String status) {
        this.status = status;
        return this;
    }

    @Exclude
    public boolean isComplete(){
        if(this.status.equals("Complete")){
            return true;
        }
        return false;
    }
}
