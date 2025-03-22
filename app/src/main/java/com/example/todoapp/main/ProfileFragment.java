package com.example.todoapp.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.auth.AuthController;
import com.example.todoapp.auth.LoginActivity;
import com.example.todoapp.database.UserInfo;

public class ProfileFragment extends Fragment {
    public static final String USER_INFO = "USER_INFO";
    private AppCompatActivity  activity ;
    private TextView profile_TV_name;
    private TextView profile_TV_email;
    private CardView profile_CV_editDetails;
    private CardView profile_CV_logout;
    private UserInfo userInfo;
    private AuthController authController;

    public ProfileFragment(AppCompatActivity activity) {
        this.activity = activity;
    }
    public void setUser(UserInfo userInfo) {
        this.userInfo = userInfo;
        ShowUserData();
    }

    private void ShowUserData(){
        profile_TV_name.setText(userInfo.getFirstName() + " " + userInfo.getLastName());
        profile_TV_email.setText(userInfo.getEmail());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void findViews(View view) {
        profile_TV_name = view.findViewById(R.id.profile_TV_name);
        profile_TV_email = view.findViewById(R.id.profile_TV_email);
        profile_CV_editDetails = view.findViewById(R.id.profile_CV_editDetails);
        profile_CV_logout = view.findViewById(R.id.profile_CV_logout);
    }
    private void initVars() {



        authController = new AuthController();

        profile_CV_editDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, EditProfileActivity.class);
                intent.putExtra(USER_INFO, userInfo);
                startActivity(intent);
            }
        });

        profile_CV_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, LoginActivity.class);
                startActivity(intent);
                authController.logoutUser();
                activity.finish();
            }
        });
    }




}