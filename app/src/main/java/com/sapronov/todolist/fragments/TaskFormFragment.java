package com.sapronov.todolist.fragments;

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

import com.sapronov.todolist.R;
import com.sapronov.todolist.activities.MainActivity;
import com.sapronov.todolist.data.SqlStore;
import com.sapronov.todolist.model.Task;

public class TaskFormFragment extends Fragment  {

    private View view;
    private Bundle args;
    private EditText name;
    private EditText desc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.task_form, container, false);
        args = getArguments();
        Button save = view.findViewById(R.id.save_button);
        name = view.findViewById(R.id.task_name);
        desc = view.findViewById(R.id.task_desc);
        if (args != null) {
            name.setText(args.getString("name"));
            desc.setText(args.getString("desc"));
        }
        save.setOnClickListener(this::saveBtn);
        return view;
    }

    private void saveBtn(View v) {
        if (name.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter the Task name!", Toast.LENGTH_SHORT).show();
        } else {
            selectedVariant();
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
        }
    }

    private void selectedVariant() {
        if (args != null) {
            Task task = new Task(name.getText().toString(),
                    desc.getText().toString(), args.getInt("closed"));
            task.setId(args.getInt("id"));
            SqlStore.getStore(getContext()).update(task);
        } else {
            Task task = new Task(name.getText().toString(),
                    desc.getText().toString(), 0);
            SqlStore.getStore(getContext()).add(task);
        }
    }
}
