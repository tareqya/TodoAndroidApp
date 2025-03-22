package com.example.todoapp.main;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.todoapp.R;
import com.example.todoapp.adapter.TaskAdapter;
import com.example.todoapp.callback.TaskAdapterCallBack;
import com.example.todoapp.callback.TaskCallBack;
import com.example.todoapp.database.TaskDatabaseController;
import com.example.todoapp.database.TodoTask;
import com.example.todoapp.database.UserInfo;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class HomeFragment extends Fragment {
    public static final String TASK = "task";
    private String selectedDate;
    private UserInfo currentUser;
    private AppCompatActivity  activity ;
    private TextView frg_home_TV_title;
    private FloatingActionButton frg_home_FBA_createTask;
    private TaskDatabaseController taskDatabaseController;
    private RecyclerView frg_home_RV_tasks;

    public HomeFragment(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void setUser(UserInfo userInfo){
        this.currentUser = userInfo;
        frg_home_TV_title.setText("Hello " + userInfo.getFirstName());
        fetchTasks();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        frg_home_FBA_createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
            }
        });

        taskDatabaseController = new TaskDatabaseController();

        taskDatabaseController.setTaskCallBack(new TaskCallBack() {
            @Override
            public void TaskCreateComplete(Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(activity, "Task added successfully", Toast.LENGTH_SHORT).show();
                }else{
                    String err = task.getException().getMessage().toString();
                    Toast.makeText(activity, "Failed to add task, " + err , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void FetchTasksComplete(ArrayList<TodoTask> tasks) {

                TaskAdapter taskAdapter = new TaskAdapter(activity, tasks);
                taskAdapter.setTaskAdapterCallBack(new TaskAdapterCallBack() {
                    @Override
                    public void onClick(TodoTask todoTask, int position) {
                        // open task activity
                        Intent intent = new Intent(activity, TaskActivity.class);
                        intent.putExtra(TASK, todoTask);

                        startActivity(intent);
                    }
                });
                frg_home_RV_tasks.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                frg_home_RV_tasks.setHasFixedSize(true);
                frg_home_RV_tasks.setItemAnimator(new DefaultItemAnimator());
                frg_home_RV_tasks.setAdapter(taskAdapter);
            }

            @Override
            public void OnTaskSaveComplete(Task<Void> task) {

            }
        });
    }

    private void findViews(View view) {
        frg_home_TV_title = view.findViewById(R.id.frg_home_TV_title);
        frg_home_FBA_createTask = view.findViewById(R.id.frg_home_FBA_createTask);
        frg_home_RV_tasks = view.findViewById(R.id.frg_home_RV_tasks);
    }

    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Enter Your Task Title");

        // Create a LinearLayout to hold the input fields
        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(50, 20, 50, 20);

        // Create an EditText field for the name input
        final EditText input = new EditText(activity);
        input.setHint("Enter task title");
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(input);

        // Create a TextView to display the selected date
        final TextView dateTextView = new TextView(activity);
        dateTextView.setText("Task Deadline: Not Set");
        dateTextView.setPadding(0, 20, 0, 20);
        layout.addView(dateTextView);

        // Create a button to open the date picker dialog
        Button datePickerButton = new Button(activity);
        datePickerButton.setText("Select Deadline Date");
        layout.addView(datePickerButton);

        // Set the button click listener
        datePickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(activity,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                selectedDate = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                                dateTextView.setText("Selected Date: " + selectedDate);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String taskTile = input.getText().toString();
                String deadline = selectedDate;

                if(deadline == null || taskTile.isEmpty()){
                    Toast.makeText(activity, "Please enter the task title and deadline", Toast.LENGTH_SHORT).show();
                    return;
                }

                TodoTask todoTask = new TodoTask();
                todoTask.setDeadline(deadline);
                todoTask.setUserId(currentUser.getUid());
                todoTask.setTitle(taskTile);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate = sdf.format(Calendar.getInstance().getTime());
                todoTask.setCreateTime(currentDate);
                // create task
                AddNewTask(todoTask);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Show the dialog
        builder.show();
    }

    private void AddNewTask(TodoTask todoTask) {
        taskDatabaseController.AddNewTask(todoTask);
    }

    private void  fetchTasks(){
        String uid = this.currentUser.getUid();
        this.taskDatabaseController.FetchUserTasks(uid);
    }
}