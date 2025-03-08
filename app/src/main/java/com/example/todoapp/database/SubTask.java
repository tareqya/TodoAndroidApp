package com.example.todoapp.database;

public class SubTask {
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
}
