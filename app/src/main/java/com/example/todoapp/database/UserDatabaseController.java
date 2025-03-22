package com.example.todoapp.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.todoapp.callback.UserInfoCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

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

    public void fetchUserData(String uid){
        this.db.collection(USERS_TABLE).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value == null) return;
                UserInfo userInfo = value.toObject(UserInfo.class);
                userInfo.setUid(uid);
                userInfoCallBack.FetchUserInfoComplete(userInfo);
            }
        });
    }

    public void saveUserInfo(UserInfo userInfo){
        this.db.collection(USERS_TABLE).document(userInfo.getUid()).set(userInfo)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        userInfoCallBack.OnUserInfoSaveComplete(task);
                    }
                });
    }
}
