package com.example.todoapp.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.callback.UserInfoCallBack;
import com.example.todoapp.database.UserDatabaseController;
import com.example.todoapp.database.UserInfo;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.auth.User;

public class EditProfileActivity extends AppCompatActivity {

    private TextInputLayout profileEdit_TF_firstName;
    private TextInputLayout profileEdit_TF_lastName;
    private Button editProfile_BTN_update;
    private UserInfo userInfo;
    private UserDatabaseController userDatabaseController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        Intent intent = getIntent();
        userInfo = (UserInfo)  intent.getSerializableExtra(ProfileFragment.USER_INFO);
        findViews();
        initVars();
    }

    private void findViews() {
        profileEdit_TF_firstName = findViewById(R.id.profileEdit_TF_firstName);
        profileEdit_TF_lastName = findViewById(R.id.profileEdit_TF_lastName);
        editProfile_BTN_update = findViewById(R.id.editProfile_BTN_update);
    }

    private void initVars() {

        profileEdit_TF_firstName.getEditText().setText(userInfo.getFirstName());
        profileEdit_TF_lastName.getEditText().setText(userInfo.getLastName());
        userDatabaseController = new UserDatabaseController();
        userDatabaseController.setUserInfoCallBack(new UserInfoCallBack() {
            @Override
            public void CreateUserInfoComplete(Task<Void> task) {

            }

            @Override
            public void FetchUserInfoComplete(UserInfo userInfo) {

            }

            @Override
            public void OnUserInfoSaveComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "User info saved successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(EditProfileActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });

        editProfile_BTN_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfo.setFirstName(profileEdit_TF_firstName.getEditText().getText().toString());
                userInfo.setLastName(profileEdit_TF_lastName.getEditText().getText().toString());
                userDatabaseController.saveUserInfo(userInfo);

            }
        });
    }


}