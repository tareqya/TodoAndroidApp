package com.example.todoapp.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.callback.TaskCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;

public class TaskDatabaseController extends DatabaseManager {
    public static final String TASKS_TABLE = "Tasks";
    private TaskCallBack taskCallBack;

    public TaskDatabaseController(){
        super();

    }

    public void setTaskCallBack(TaskCallBack taskCallBack){
        this.taskCallBack = taskCallBack;
    }
    public void AddNewTask(TodoTask task){
        this.db.collection(TASKS_TABLE).document().set(task)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        taskCallBack.TaskCreateComplete(task);
                    }
                });
    }

    public void FetchUserTasks(String uid){
        this.db.collection(TASKS_TABLE).whereEqualTo("userId", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && !value.isEmpty()) {
                            ArrayList<TodoTask> tasks = new ArrayList<>();
                            for (DocumentSnapshot document : value.getDocuments()) {
                                TodoTask task = document.toObject(TodoTask.class);
                                task.setUid(document.getId());
                                tasks.add(task);
                            }
                            Collections.sort(tasks);
                            taskCallBack.FetchTasksComplete(tasks);
                        }
                    }
                });
    }
}
