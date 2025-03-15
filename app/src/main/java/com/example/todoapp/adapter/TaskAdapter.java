package com.example.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.callback.TaskAdapterCallBack;
import com.example.todoapp.database.TodoTask;

import java.util.ArrayList;

public class TaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<TodoTask> tasks;
    private Context context;
    private TaskAdapterCallBack taskAdapterCallBack;
    public TaskAdapter(Context context, ArrayList<TodoTask> tasks){
        this.tasks = tasks;
        this.context = context;
    }
    public void setTaskAdapterCallBack(TaskAdapterCallBack taskAdapterCallBack){
        this.taskAdapterCallBack = taskAdapterCallBack;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TaskViewHolder taskViewHolder = (TaskViewHolder) holder;
        TodoTask todoTask = this.tasks.get(position);

        taskViewHolder.task_TV_taskName.setText(todoTask.getTitle());
        taskViewHolder.task_TV_taskTime.setText(todoTask.getCreateTime());
        taskViewHolder.task_PB_taskProgress.setProgress(todoTask.getProgress());
        taskViewHolder.task_TV_taskProgress.setText(todoTask.getProgress() + "%");
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    private TodoTask getItem(int position) {
        return tasks.get(position);
    }

    public class TaskViewHolder extends  RecyclerView.ViewHolder {

        private TextView task_TV_taskName;
        private TextView task_TV_taskTime;
        private ProgressBar task_PB_taskProgress;
        private TextView task_TV_taskProgress;
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);

            task_TV_taskName = itemView.findViewById(R.id.task_TV_taskName);
            task_TV_taskTime = itemView.findViewById(R.id.task_TV_taskTime);
            task_PB_taskProgress = itemView.findViewById(R.id.task_PB_taskProgress);
            task_TV_taskProgress = itemView.findViewById(R.id.task_TV_taskProgress);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    TodoTask todoTask = getItem(position);
                    taskAdapterCallBack.onClick(todoTask, position);
                }
            });
        }
    }
}
