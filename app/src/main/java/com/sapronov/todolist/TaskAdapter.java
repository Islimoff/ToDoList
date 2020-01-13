package com.sapronov.todolist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Locale;

class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

    private Context parent;

    public TaskAdapter( Context parent) {
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
        final Task task = Store.getStore().get(position);
        TextView text = holder.view.findViewById(R.id.info);
        TextView createDate = holder.view.findViewById(R.id.create_date);
        text.setText(task.getName());
        createDate.setText(format(task.getCreate()));
        text.setOnClickListener(view ->{
            Intent intent = new Intent(parent, TaskDetailsActivity.class);
            intent.putExtra("position",position);
            parent.startActivity(intent);
        } );
        CheckBox done=holder.view.findViewById(R.id.checkBox);
        done.setChecked(task.getClosed());
        done.setOnCheckedChangeListener(((view, isClosed) -> task.setClosed(isClosed)));

    }

    private String format(Calendar date){
        return String.format(Locale.getDefault(),"%02d.%02d.%d",
                date.get(Calendar.DAY_OF_MONTH),date.get(Calendar.MONTH),date.get(Calendar.YEAR));

    }

    @Override
    public int getItemCount() {
        return Store.getStore().size();
    }
}
