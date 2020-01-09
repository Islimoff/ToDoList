package com.sapronov.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TaskFormFragment extends Fragment {

    private View view;
    private int position;
    private EditText name;
    private EditText desc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        position=getActivity().getIntent().getIntExtra("position",-1);
        view=inflater.inflate(R.layout.task_form,container,false);
        Button save=view.findViewById(R.id.save_button);
        name = view.findViewById(R.id.task_name);
        desc = view.findViewById(R.id.task_desc);
        save.setOnClickListener(this::saveBtn);
        if(position!=-1){
            Task task=Store.getStore().getTask(position);
            name.setText(task.getName());
            desc.setText(task.getDesc());
        }
        return view;
    }

    private void saveBtn(View v){
        if(name.getText().toString().equals("")){
            Toast.makeText(getContext(),"Enter the Task name!",Toast.LENGTH_SHORT).show();
        }else {
            selectedVariant();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void selectedVariant(){
        if (position != -1) {
            Store.getStore().getTask(position)
                    .editTask(name.getText().toString(),desc.getText().toString());
        }else {
            Store.getStore()
                    .addTask(new Task(name.getText().toString(),desc.getText().toString()));
        }
    }
}
