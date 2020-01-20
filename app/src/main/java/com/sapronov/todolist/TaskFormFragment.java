package com.sapronov.todolist;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

import com.sapronov.todolist.data.TodoBaseHelper;
import com.sapronov.todolist.data.TodoDbSchema;
import com.sapronov.todolist.data.TodoDbSchema.TaskTable;

public class TaskFormFragment extends Fragment {

    private View view;
    private Bundle args;
    private EditText name;
    private EditText desc;
    private SQLiteDatabase store;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.store = new TodoBaseHelper(this.getContext()).getWritableDatabase();
        view = inflater.inflate(R.layout.task_form, container, false);
        args = getArguments();
        Button save = view.findViewById(R.id.save_button);
        name = view.findViewById(R.id.task_name);
        desc = view.findViewById(R.id.task_desc);
        if(args!=null){
            name.setText(args.getString("name"));
            desc.setText(args.getString("desc"));
        }
        save.setOnClickListener(this::saveBtn);
        this.store = new TodoBaseHelper(getContext()).getWritableDatabase();
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
            store.update(TaskTable.NAME, getContentValues(), "id = ?", new String[]{String.valueOf(args.getInt("id"))});
        } else {
            store.insert(TaskTable.NAME, null, getContentValues());
        }
    }

    private ContentValues getContentValues(){
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.NAME, name.getText().toString());
        values.put(TaskTable.Cols.TITLE, desc.getText().toString());
        values.put(TaskTable.Cols.CLOSED, false);
        return values;
    }
}
