package com.example.todoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todoapp.R;
import com.example.todoapp.callback.SubTaskCallback;
import com.example.todoapp.database.SubTask;


import java.util.ArrayList;

public class SubTaskAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<SubTask> subTasks;
    private Context context;
    private SubTaskCallback subTaskCallback;

    public SubTaskAdapter(Context context, ArrayList<SubTask> subTasks){
        this.context = context;
        this.subTasks = subTasks;
    }

    public void setSubTasksCallback(SubTaskCallback subTaskCallback){
        this.subTaskCallback = subTaskCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_task_item, parent, false);
        return new SubTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SubTaskViewHolder subTaskViewHolder = (SubTaskViewHolder) holder;
        SubTask subTask = getItem(position);

        subTaskViewHolder.subTask_TV_taskTitle.setText(subTask.getTitle());
        subTaskViewHolder.subTask_CB_taskStatus.setChecked(subTask.isComplete());

    }

    @Override
    public int getItemCount() {
        return subTasks.size();
    }
    private SubTask getItem(int position) {
        return subTasks.get(position);
    }


    public class SubTaskViewHolder extends  RecyclerView.ViewHolder {
        private TextView subTask_TV_taskTitle;
        private CheckBox subTask_CB_taskStatus;
        private ImageView subTask_IV_removeTask;

        public SubTaskViewHolder(@NonNull View itemView) {
            super(itemView);
            subTask_TV_taskTitle = itemView.findViewById(R.id.subTask_TV_taskTitle);
            subTask_CB_taskStatus = itemView.findViewById(R.id.subTask_CB_taskStatus);
            subTask_IV_removeTask = itemView.findViewById(R.id.subTask_IV_removeTask);

            subTask_IV_removeTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    SubTask subTask = getItem(pos);
                    subTaskCallback.OnDeleteClicked(subTask);
                }
            });

            subTask_CB_taskStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    SubTask subTask = getItem(pos);
                    boolean status = subTask_CB_taskStatus.isChecked();
                    subTaskCallback.OnStatusChanged(subTask, status);
                }
            });

        }
    }
}
