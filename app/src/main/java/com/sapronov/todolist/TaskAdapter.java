package com.sapronov.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private List<Task> tasks;
    private Context parent;

    public TaskAdapter(List<Task> tasks, Context parent) {
        this.tasks = tasks;
        this.parent = parent;
    }

    public class TaskHolder extends RecyclerView.ViewHolder {

        public View view;

        public TaskHolder(@NonNull View view) {
            super(view);
            this.view = itemView;
        }
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.info_task, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        final Task task = this.tasks.get(position);
        TextView text = holder.view.findViewById(R.id.info);
        text.setText(task.getName());
        text.setOnClickListener(this::showTask);
    }

    @Override
    public int getItemCount() {
        return this.tasks.size();
    }

    private void showTask(View view) {
        Intent intent = new Intent(parent, TaskActivity.class);
        parent.startActivity(intent);
    }
}
