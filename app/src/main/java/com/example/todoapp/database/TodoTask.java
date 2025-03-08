package com.example.todoapp.database;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class TodoTask extends Uid {
    private String title;
    private String userId;
    private String deadline;
    private String createTime;
    private ArrayList<SubTask> subTasks;

    public TodoTask() {
        subTasks = new ArrayList<>();
    }

    public String getTitle() {
        return title;
    }

    public TodoTask setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public TodoTask setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    public String getDeadline() {
        return deadline;
    }

    public TodoTask setDeadline(String deadline) {
        this.deadline = deadline;
        return this;
    }

    public String getCreateTime() {
        return createTime;
    }

    public TodoTask setCreateTime(String createTime) {
        this.createTime = createTime;
        return this;
    }
    @Exclude
    public String getStatus(){
        for(int i = 0 ; i < this.subTasks.size(); i++){
            if(!this.subTasks.get(i).getStatus().equals("Complete"))
                return "Not Complete";
        }
        return "Complete";
    }
}
