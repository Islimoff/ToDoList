package com.sapronov.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recycler = findViewById(R.id.items_list);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(new TaskAdapter(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item:
                Intent intent = new Intent(this, TaskFormActivity.class);
                startActivity(intent);
                return true;
            case R.id.delete_item:
//                DialogFragment dialog = new ConfirmDeleteExamsListFragment();
//                dialog.show(getSupportFragmentManager(), "dialog_tag");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
