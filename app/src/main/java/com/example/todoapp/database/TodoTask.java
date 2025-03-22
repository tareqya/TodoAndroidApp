package com.example.todoapp.database;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class TodoTask extends Uid implements Comparable<TodoTask>, Serializable {
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

    public ArrayList<SubTask> getSubTasks() {
        return this.subTasks;
    }

    @Exclude
    public boolean addTask(String task){
        for(int i = 0 ; i < this.subTasks.size(); i++){
            if(this.subTasks.get(i).getTitle().equals(task))
                return false;
        }
        // add new task
        SubTask subTask = new SubTask();
        subTask.setTitle(task);
        subTask.setStatus("Not Complete");
        this.subTasks.add(subTask);
        return true;
    }

    @Exclude
    public boolean updateSubTaskStatus(SubTask subTask, boolean status){
        for(int i = 0 ; i < this.subTasks.size(); i++){
            if(this.subTasks.get(i).getTitle().equals(subTask.getTitle())){
                String currentStatus = "Not Complete";
                if(status){
                    currentStatus = "Complete";
                }
                this.subTasks.get(i).setStatus(currentStatus);
                return true;
            }
        }
        return false;
    }
    @Exclude
    public boolean removeTask(SubTask subTask){
        for(int i = 0 ; i < this.subTasks.size(); i++){
            if(this.subTasks.get(i).getTitle().equals(subTask.getTitle())){
                this.subTasks.remove(i);
                return true;
            }
        }
        return false;
    }

    @Exclude
    public String getStatus(){
        for(int i = 0 ; i < this.subTasks.size(); i++){
            if(!this.subTasks.get(i).getStatus().equals("Complete"))
                return "Not Complete";
        }
        return "Complete";
    }

    @Exclude
    public int getProgress(){
        int count = 0;

        if(this.subTasks.isEmpty())
            return 0;

        for(int i = 0 ; i < this.subTasks.size(); i++){
            if(this.subTasks.get(i).getStatus().equals("Complete")){
               count ++;
            }
        }

        double progress = (double) count / this.subTasks.size(); // 0.333
        progress = progress * 100;

        return (int) Math.round(progress);
    }

    @Override
    public int compareTo(TodoTask todoTask) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()); // Adjusted format

        try {
            Date currentDate = dateFormat.parse(this.deadline);
            Date otherDate = dateFormat.parse(todoTask.getDeadline());

            return currentDate.compareTo(otherDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return 0; // Return 0 if parsing fails
        }
    }
}
