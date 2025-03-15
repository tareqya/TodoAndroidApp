package com.example.todoapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.database.TodoTask;
import com.google.android.material.textfield.TextInputLayout;

public class TaskActivity extends AppCompatActivity {
    private TextView taskActivity_TV_title;
    private TextView taskActivity_TV_createTime;
    private TextView taskActivity_TV_deadline;
    private TextInputLayout taskActivity_TF_task;
    private Button taskActivity_BTN_addSubTask;
    private RecyclerView taskActivity_RV_subTasks;
    private TodoTask todoTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent = getIntent();

        todoTask = (TodoTask) intent.getSerializableExtra(HomeFragment.TASK);
        findViews();
        initVars();
    }
    private void findViews() {
        taskActivity_TV_title = findViewById(R.id.taskActivity_TV_title);
        taskActivity_TF_task = findViewById(R.id.taskActivity_TF_task);
        taskActivity_BTN_addSubTask = findViewById(R.id.taskActivity_BTN_addSubTask);
        taskActivity_RV_subTasks = findViewById(R.id.taskActivity_RV_subTasks);
        taskActivity_TV_createTime = findViewById(R.id.taskActivity_TV_createTime);
        taskActivity_TV_deadline = findViewById(R.id.taskActivity_TV_deadline);

    }
    private void initVars() {
        taskActivity_TV_title.setText(todoTask.getTitle());
        taskActivity_TV_deadline.setText("Deadline: " + todoTask.getDeadline());
        taskActivity_TV_createTime.setText("Create Time: "+todoTask.getCreateTime());

        taskActivity_BTN_addSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subTaskName = taskActivity_TF_task.getEditText().getText().toString();
                if(subTaskName.isEmpty()){
                    Toast.makeText(TaskActivity.this, "Task name cant be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                todoTask.addTask(subTaskName);
            }
        });
    }


}