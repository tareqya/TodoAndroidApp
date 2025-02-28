package com.example.todoapp.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.callback.AuthCallBack;
import com.example.todoapp.callback.UserInfoCallBack;
import com.example.todoapp.database.UserDatabaseController;
import com.example.todoapp.database.UserInfo;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;

public class SignupActivity extends AppCompatActivity {
    private TextInputLayout signup_TF_email;
    private TextInputLayout signup_TF_firstname;
    private TextInputLayout signup_TF_lastname;
    private TextInputLayout signup_TF_password;
    private TextInputLayout signup_TF_confirmPassword;
    private Button signup_BTN_createAccount;
    private ProgressBar signup_progress_loading;
    private AuthController authController;
    private UserDatabaseController userDatabaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViews();
        initVars();
    }

    private void findViews() {
        signup_TF_email = findViewById(R.id.signup_TF_email);
        signup_TF_firstname = findViewById(R.id.signup_TF_firstname);
        signup_TF_lastname = findViewById(R.id.signup_TF_lastname);
        signup_TF_password = findViewById(R.id.signup_TF_password);
        signup_TF_confirmPassword = findViewById(R.id.signup_TF_confirmPassword);
        signup_BTN_createAccount = findViewById(R.id.signup_BTN_createAccount);
        signup_progress_loading = findViewById(R.id.signup_progress_loading);
    }

    private void initVars() {
        userDatabaseController = new UserDatabaseController();
        authController = new AuthController();


        userDatabaseController.setUserInfoCallBack(new UserInfoCallBack() {
            @Override
            public void CreateUserInfoComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    signup_progress_loading.setVisibility(View.INVISIBLE);
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, err, Toast.LENGTH_SHORT).show();
                }

            }
        });

        authController.setAuthCallBack(new AuthCallBack() {
            @Override
            public void CreateUserAuthComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String email = signup_TF_email.getEditText().getText().toString();
                    String firstname = signup_TF_firstname.getEditText().getText().toString();
                    String lastname = signup_TF_lastname.getEditText().getText().toString();

                    String uid = authController.getCurrentUser().getUid();
                    UserInfo userInfo = new UserInfo();
                    userInfo.setFirstName(firstname);
                    userInfo.setLastName(lastname);
                    userInfo.setEmail(email);
                    userInfo.setUid(uid);
                    userDatabaseController.createUserInfo(userInfo);
                }else{
                    signup_progress_loading.setVisibility(View.INVISIBLE);
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(SignupActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void LoginUserAuthComplete(Task<AuthResult> task) {

            }
        });

        signup_BTN_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // check if user fill all inputs
                String email = signup_TF_email.getEditText().getText().toString();
                String firstname = signup_TF_firstname.getEditText().getText().toString();
                String lastname = signup_TF_lastname.getEditText().getText().toString();
                String password = signup_TF_password.getEditText().getText().toString();
                String confirmPassword = signup_TF_confirmPassword.getEditText().getText().toString();

                if(email.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()){
                    Toast.makeText(SignupActivity.this, "All fields are required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // check if password and confirm password are equals
                if(!password.equals(confirmPassword)){
                    Toast.makeText(SignupActivity.this, "Mismatch between password and confirm password", Toast.LENGTH_SHORT).show();
                    return;
                }

                // create new user
                signup_progress_loading.setVisibility(View.VISIBLE);

                UserAuth userAuth = new UserAuth();
                userAuth.setEmail(email);
                userAuth.setPassword(password);

                authController.CreateUserAuth(userAuth);
            }
        });
    }

}