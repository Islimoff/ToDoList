package com.sapronov.todolist.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.sapronov.todolist.R;

public class TaskDetailsFragment extends Fragment {

    private Bundle args;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_details, container, false);
        args = getArguments();
        TextView detailName = view.findViewById(R.id.detail_name);
        TextView detailDesc = view.findViewById(R.id.detail_desc);
        detailName.setText(args.getString("name"));
        detailDesc.setText(args.getString("desc"));
        Button back = view.findViewById(R.id.back_button);
        back.setOnClickListener(this::backBtn);
        Button edit = view.findViewById(R.id.edit_button);
        edit.setOnClickListener(this::editBtn);
        return view;
    }

    private void backBtn(View view) {
        getActivity().onBackPressed();
    }

    private void editBtn(View view) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = new TaskFormFragment();
        fragment.setArguments(args);
        fm.beginTransaction()
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }
}
