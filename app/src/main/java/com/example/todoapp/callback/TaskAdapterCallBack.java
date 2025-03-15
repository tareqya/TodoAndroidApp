package com.example.todoapp.callback;

import com.example.todoapp.database.TodoTask;

public interface TaskAdapterCallBack {
    void onClick(TodoTask task, int position);
}
