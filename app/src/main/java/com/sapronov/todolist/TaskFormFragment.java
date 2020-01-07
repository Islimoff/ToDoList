package com.sapronov.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TaskFormFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.task_form,container,false);
        Button save=view.findViewById(R.id.save_button);
        save.setOnClickListener(this::saveBtn);
        return view;
    }

    private void saveBtn(View v){
        EditText name = view.findViewById(R.id.task_name);
        EditText desc = view.findViewById(R.id.task_desc);
        Store.getStore().addTask(new Task(name.getText().toString(),desc.getText().toString()));
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
