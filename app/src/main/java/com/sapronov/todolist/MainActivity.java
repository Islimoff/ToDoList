package com.sapronov.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<Task>tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button add =findViewById(R.id.button);
        add.setOnClickListener(this::addBtn);
        recycler = findViewById(R.id.items_list);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tasks=new ArrayList<>();
//        updateUI();
    }

    private void addBtn(View view){
        Intent intent = new Intent(this, TaskActivity.class);
        startActivity(intent);
    }

    private void updateUI() {
        recycler.setAdapter(new TaskAdapter(tasks,this));
    }
}
