package com.sapronov.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private List<Task>tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycler = findViewById(R.id.items_list);
        recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        tasks=new ArrayList<>();
        updateUI();
    }

    private void addBtn(View view){
        
    }

    private void updateUI() {
        recycler.setAdapter(new TaskAdapter(tasks,this));
    }
}
