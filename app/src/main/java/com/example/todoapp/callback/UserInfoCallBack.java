package com.example.todoapp.callback;

import com.example.todoapp.database.UserInfo;
import com.google.android.gms.tasks.Task;

public interface UserInfoCallBack {
    void CreateUserInfoComplete(Task<Void> task);
    void FetchUserInfoComplete(UserInfo userInfo);
    void OnUserInfoSaveComplete(Task<Void> task);
}
