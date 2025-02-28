package com.example.todoapp.database;

import androidx.annotation.NonNull;

import com.example.todoapp.callback.UserInfoCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class UserDatabaseController extends DatabaseManager{
    public static final String USERS_TABLE = "Users";
    private UserInfoCallBack userInfoCallBack;
    public UserDatabaseController(){
        super();
    }

    public void setUserInfoCallBack(UserInfoCallBack userInfoCallBack){
        this.userInfoCallBack = userInfoCallBack;
    }

    public void createUserInfo(UserInfo userInfo){
        this.db.collection(USERS_TABLE).document(userInfo.getUid()).set(userInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        userInfoCallBack.CreateUserInfoComplete(task);
                    }
                });
    }
}
