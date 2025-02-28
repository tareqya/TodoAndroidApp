package com.example.todoapp.callback;

import com.google.android.gms.tasks.Task;

public interface UserInfoCallBack {
    void CreateUserInfoComplete(Task<Void> task);
}
