package com.example.todoapp.callback;

import com.example.todoapp.database.TodoTask;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public interface TaskCallBack {
    void TaskCreateComplete(Task<Void> task);
    void FetchTasksComplete(ArrayList<TodoTask> tasks);
    void OnTaskSaveComplete(Task<Void> task);
}
