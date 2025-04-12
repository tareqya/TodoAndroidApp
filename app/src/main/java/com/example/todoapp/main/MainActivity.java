package com.example.todoapp.main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
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
import com.example.todoapp.utils.NotificationReceiver;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static String CHANNEL_ID = "1002";
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!checkPermissions()) {
                requestPermissions();
            }
        }
        createNotificationChannel();
    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public boolean checkPermissions() {
        return (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{
                        Manifest.permission.POST_NOTIFICATIONS,
                },
                100
        );
    }
    private void findViews() {
        main_frame_home = findViewById(R.id.main_frame_home);
        main_frame_profile = findViewById(R.id.main_frame_profile);
        main_BN = findViewById(R.id.main_BN);

    }

    private void initVars() {
        profileFragment = new ProfileFragment(this);
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
                profileFragment.setUser(userInfo);
            }

            @Override
            public void OnUserInfoSaveComplete(Task<Void> task) {

            }
        });
        String uid = authController.getCurrentUser().getUid();
        userDatabaseController.fetchUserData(uid);
    }


    public static void createNotification(Context context, String body) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("TODO APP TASK - Reminder")
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TODO APP";
            String description = "TODO APP reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void scheduleNotification(Calendar calendar, String msg) {

        // Create an intent to trigger the BroadcastReceiver
        Intent intent = new Intent(this, NotificationReceiver.class);
        intent.putExtra("body", msg);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Schedule the alarm
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}