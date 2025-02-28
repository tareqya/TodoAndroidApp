package com.example.todoapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.callback.AuthCallBack;
import com.example.todoapp.main.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout login_TF_email;
    private TextInputLayout login_TF_password;
    private Button login_BTN_loginAction;
    private Button login_BTN_signup;
    private ProgressBar login_progress_loading;
    private AuthController authController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViews();
        initVars();
    }

    private void findViews() {
        login_TF_email = findViewById(R.id.login_TF_email);
        login_TF_password = findViewById(R.id.login_TF_password);
        login_BTN_loginAction = findViewById(R.id.login_BTN_loginAction);
        login_BTN_signup = findViewById(R.id.login_BTN_signup);
        login_progress_loading = findViewById(R.id.login_progress_loading);
    }

    private void initVars() {
        authController = new AuthController();
        authController.setAuthCallBack(new AuthCallBack() {
            @Override
            public void CreateUserAuthComplete(Task<AuthResult> task) {

            }

            @Override
            public void LoginUserAuthComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, err, Toast.LENGTH_SHORT).show();
                    login_progress_loading.setVisibility(View.INVISIBLE);
                }
            }
        });

        login_BTN_loginAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = login_TF_email.getEditText().getText().toString();
                String password = login_TF_password.getEditText().getText().toString();

                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "You should enter the email and password", Toast.LENGTH_SHORT).show();
                    return;
                }
                login_progress_loading.setVisibility(View.VISIBLE);
                UserAuth userAuth = new UserAuth();
                userAuth.setPassword(password).setEmail(email);

                authController.loginUser(userAuth);

            }
        });

        login_BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        authController = new AuthController();
        if(authController.getCurrentUser() != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}