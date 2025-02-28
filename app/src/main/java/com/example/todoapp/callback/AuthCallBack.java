package com.example.todoapp.callback;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface AuthCallBack {
    void CreateUserAuthComplete( Task<AuthResult> task);
    void LoginUserAuthComplete(Task<AuthResult> task);
}
