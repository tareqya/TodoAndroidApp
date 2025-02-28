package com.example.todoapp.auth;

import androidx.annotation.NonNull;

import com.example.todoapp.callback.AuthCallBack;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthController {
    private FirebaseAuth mAuth;
    private AuthCallBack authCallBack;

    public AuthController() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public void setAuthCallBack(AuthCallBack authCallBack){
        this.authCallBack = authCallBack;
    }

    public void CreateUserAuth(UserAuth userAuth){
        this.mAuth.createUserWithEmailAndPassword(userAuth.getEmail(), userAuth.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        authCallBack.CreateUserAuthComplete(task);
                    }
                });

    }

    public void loginUser(UserAuth userAuth){
        this.mAuth.signInWithEmailAndPassword(userAuth.getEmail(), userAuth.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        authCallBack.LoginUserAuthComplete(task);
                    }
                });
    }
    public void logoutUser(){
        this.mAuth.signOut();
    }
    public FirebaseUser getCurrentUser(){
        return this.mAuth.getCurrentUser();
    }
}
