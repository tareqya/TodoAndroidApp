package com.example.todoapp.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.todoapp.R;
import com.example.todoapp.auth.AuthController;
import com.example.todoapp.callback.UserInfoCallBack;
import com.example.todoapp.database.UserDatabaseController;
import com.example.todoapp.database.UserInfo;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout main_frame_home;
    private FrameLayout main_frame_profile;
    private BottomNavigationView main_BN;
    private ProfileFragment profileFragment;
    private HomeFragment homeFragment;
    private AuthController authController;
    private UserDatabaseController userDatabaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initVars();
        fetchCurrentUserData();
    }

    private void findViews() {
        main_frame_home = findViewById(R.id.main_frame_home);
        main_frame_profile = findViewById(R.id.main_frame_profile);
        main_BN = findViewById(R.id.main_BN);

    }

    private void initVars() {
        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment(this);

        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_home, homeFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.main_frame_profile, profileFragment).commit();

        main_frame_profile.setVisibility(View.INVISIBLE);

        main_BN.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.profile){
                    main_frame_profile.setVisibility(View.VISIBLE);
                    main_frame_home.setVisibility(View.INVISIBLE);
                }else if(item.getItemId() == R.id.home){
                    main_frame_profile.setVisibility(View.INVISIBLE);
                    main_frame_home.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }


    private void fetchCurrentUserData (){
        authController = new AuthController();
        userDatabaseController = new UserDatabaseController();
        userDatabaseController.setUserInfoCallBack(new UserInfoCallBack() {
            @Override
            public void CreateUserInfoComplete(Task<Void> task) {

            }

            @Override
            public void FetchUserInfoComplete(UserInfo userInfo) {
                homeFragment.setUser(userInfo);
            }
        });
        String uid = authController.getCurrentUser().getUid();
        userDatabaseController.fetchUserData(uid);
    }
}