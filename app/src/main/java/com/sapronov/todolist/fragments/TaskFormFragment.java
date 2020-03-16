package com.sapronov.todolist.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sapronov.todolist.R;
import com.sapronov.todolist.activities.MainActivity;
import com.sapronov.todolist.data.SqlStore;
import com.sapronov.todolist.model.Task;

import static android.app.Activity.RESULT_OK;

public class TaskFormFragment extends Fragment  {

    private View view;
    private ImageView photo;
    private Bundle args;
    private EditText name;
    private EditText desc;
    private int REQUEST_IMAGE_CAPTURE=1;

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
        photo = view.findViewById(R.id.photo);
        photo.setOnClickListener(this::savePhoto);
        return view;
    }

    private void savePhoto(View view){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(imageBitmap);
        }
    }

    private void saveBtn(View view) {
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
