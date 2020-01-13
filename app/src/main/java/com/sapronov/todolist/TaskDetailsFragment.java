package com.sapronov.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TaskDetailsFragment extends Fragment {

    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_details, container, false);
        position=getActivity().getIntent().getIntExtra("position",0);
        Task task=Store.getStore().get(position);
        TextView detailName=view.findViewById(R.id.detail_name);
        TextView detailDesc=view.findViewById(R.id.detail_desc);
        detailName.setText(task.getName());
        detailDesc.setText(task.getDesc());
        Button back=view.findViewById(R.id.back_button);
        back.setOnClickListener(this::backBtn);
        Button edit=view.findViewById(R.id.edit_button);
        edit.setOnClickListener(this::editBtn);
        return view;
    }

    private void backBtn(View view){
        getActivity().onBackPressed();
    }

    private void editBtn(View view){
        Cursor cursor = this.getActivity().getContentResolver()
                .query(StoreContentProvider.CONTENT_URI, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Log.d("ContentProvider", cursor.getString(1));
        }
        Intent intent = new Intent(getActivity(), TaskFormActivity.class);
        intent.putExtra("position",position);
        startActivity(intent);
    }
}
