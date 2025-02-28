package com.example.todoapp.database;

import com.google.firebase.firestore.FirebaseFirestore;

public abstract class DatabaseManager {
    protected FirebaseFirestore db ;
    public DatabaseManager(){
        this.db = FirebaseFirestore.getInstance();
    }

}
