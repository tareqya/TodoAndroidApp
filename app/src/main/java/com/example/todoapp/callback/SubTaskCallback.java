package com.example.todoapp.callback;

import com.example.todoapp.database.SubTask;

public interface SubTaskCallback {
    void OnStatusChanged(SubTask subTask, boolean status);
    void OnDeleteClicked(SubTask subTask);
}
