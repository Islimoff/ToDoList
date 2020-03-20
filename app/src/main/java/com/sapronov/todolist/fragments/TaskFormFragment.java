package com.sapronov.todolist.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.sapronov.todolist.R;
import com.sapronov.todolist.activities.MainActivity;
import com.sapronov.todolist.data.SqlStore;
import com.sapronov.todolist.model.Task;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class TaskFormFragment extends Fragment {

    private View view;
    private ImageView photo;
    private String currentPhotoPath;
    private Bundle args;
    private EditText name;
    private EditText desc;
    static final int REQUEST_TAKE_PHOTO = 1;
    private Uri photoURI;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.task_form, container, false);
        args = getArguments();
        Button save = view.findViewById(R.id.save_button);
        name = view.findViewById(R.id.task_name);
        desc = view.findViewById(R.id.task_desc);
        photo = view.findViewById(R.id.photo);
        if (args != null) {
            name.setText(args.getString("name"));
            desc.setText(args.getString("desc"));
            photo.setImageURI(Uri.parse(args.getString("photoUri")));
        }
        save.setOnClickListener(this::saveBtn);
        photo.setOnClickListener(this::savePhoto);
        return view;
    }

    private void savePhoto(View view) {
        if (args != null) {
            Task task=SqlStore.getStore(getActivity()).getById(args.getInt("id"));
            getActivity().getContentResolver().delete(Uri.parse(task.getPhotoUri()), null, null);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(getActivity(),
                        "com.sapronov.todolist.fileprovider",
                        photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            photo.setImageURI(photoURI);
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

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void selectedVariant() {
        if(photoURI==null){
            photoURI=Uri.EMPTY;
        }
        if (args != null) {
            Task task = new Task(name.getText().toString(),
                    desc.getText().toString(),photoURI.toString(), args.getInt("closed"));
            task.setId(args.getInt("id"));
            SqlStore.getStore(getContext()).update(task);
        } else {
            Task task = new Task(name.getText().toString(),
                    desc.getText().toString(),photoURI.toString(), 0);
            SqlStore.getStore(getContext()).add(task);
        }
    }
}
