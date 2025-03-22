package com.example.todoapp.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.adapter.SubTaskAdapter;
import com.example.todoapp.callback.SubTaskCallback;
import com.example.todoapp.callback.TaskCallBack;
import com.example.todoapp.database.SubTask;
import com.example.todoapp.database.TaskDatabaseController;
import com.example.todoapp.database.TodoTask;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class TaskActivity extends AppCompatActivity {
    private TextView taskActivity_TV_title;
    private TextView taskActivity_TV_createTime;
    private TextView taskActivity_TV_deadline;
    private TextInputLayout taskActivity_TF_task;
    private Button taskActivity_BTN_addSubTask;
    private RecyclerView taskActivity_RV_subTasks;
    private TodoTask todoTask;
    private TaskDatabaseController taskDatabaseController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        Intent intent = getIntent();

        todoTask = (TodoTask) intent.getSerializableExtra(HomeFragment.TASK);
        findViews();
        initVars();
        showSubTasks(todoTask.getSubTasks());
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
        taskDatabaseController = new TaskDatabaseController();
        taskDatabaseController.setTaskCallBack(new TaskCallBack() {
            @Override
            public void TaskCreateComplete(Task<Void> task) {

            }

            @Override
            public void FetchTasksComplete(ArrayList<TodoTask> tasks) {

            }

            @Override
            public void OnTaskSaveComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(TaskActivity.this, "Task saved successfully", Toast.LENGTH_SHORT).show();
                    taskActivity_TF_task.getEditText().setText("");
                    showSubTasks(todoTask.getSubTasks());
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(TaskActivity.this, err, Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                if(todoTask.addTask(subTaskName)){
                    taskDatabaseController.updateTask(todoTask);
                }else{
                    Toast.makeText(TaskActivity.this, "This task name already exist!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void showSubTasks(ArrayList<SubTask> subTasks) {
        SubTaskAdapter subTaskAdapter = new SubTaskAdapter(this, subTasks);
        subTaskAdapter.setSubTasksCallback(new SubTaskCallback() {
            @Override
            public void OnStatusChanged(SubTask subTask, boolean status) {
                todoTask.updateSubTaskStatus(subTask, status);
                // save in database
                taskDatabaseController.updateTask(todoTask);
            }
            @Override
            public void OnDeleteClicked(SubTask subTask) {
                todoTask.removeTask(subTask);
                // save in database
                taskDatabaseController.updateTask(todoTask);
            }
        });

        taskActivity_RV_subTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        taskActivity_RV_subTasks.setHasFixedSize(true);
        taskActivity_RV_subTasks.setItemAnimator(new DefaultItemAnimator());
        taskActivity_RV_subTasks.setAdapter(subTaskAdapter);
    }

}