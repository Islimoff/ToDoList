package com.sapronov.todolist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sapronov.todolist.data.TodoBaseHelper;
import com.sapronov.todolist.data.TodoDbSchema;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskListFragment extends Fragment {

    private RecyclerView recycler;
    private SQLiteDatabase store;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_main, container, false);
        recycler = view.findViewById(R.id.items_list);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.store = new TodoBaseHelper(getContext()).getWritableDatabase();
        updateUI();
        return view;
    }

    private void updateUI() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = this.store.query(
                TodoDbSchema.TaskTable.NAME,
                null, null, null,
                null, null, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            tasks.add(new Task(cursor.getInt(cursor.getColumnIndex("id"))
                    ,cursor.getString(cursor.getColumnIndex("name"))
                    ,cursor.getString(cursor.getColumnIndex("title"))));
            cursor.moveToNext();
        }
        cursor.close();
        this.recycler.setAdapter(new TaskAdapter(tasks));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.beginTransaction()
                        .replace(R.id.content, new TaskFormFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {

        private List<Task> tasks;

        public TaskAdapter(List<Task> tasks) {
            this.tasks = tasks;
        }

        public class TaskHolder extends RecyclerView.ViewHolder {

            public View view;

            public TaskHolder(@NonNull View view) {
                super(view);
                this.view = itemView;
            }
        }

        @NonNull
        @Override
        public TaskAdapter.TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.info_task, parent, false);
            return new TaskAdapter.TaskHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TaskAdapter.TaskHolder holder, int position) {
            final Task task = tasks.get(position);
            TextView text = holder.view.findViewById(R.id.info);
            TextView createDate = holder.view.findViewById(R.id.create_date);
            text.setText(task.getName());
            createDate.setText(format(task.getCreate()));
            text.setOnClickListener(view -> {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                Fragment fragment = new TaskDetailsFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", task.getId());
                bundle.putString("name",task.getName());
                bundle.putString("desc",task.getDesc());
                fragment.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.content, fragment)
                        .addToBackStack(null)
                        .commit();
            });
            CheckBox done = holder.view.findViewById(R.id.checkBox);
            done.setChecked(task.getClosed());
            done.setOnCheckedChangeListener(((view, isClosed) -> task.setClosed(isClosed)));
        }

        private String format(Calendar date) {
            return String.format(Locale.getDefault(), "%02d.%02d.%d",
                    date.get(Calendar.DAY_OF_MONTH), date.get(Calendar.MONTH), date.get(Calendar.YEAR));
        }

        @Override
        public int getItemCount() {
            return tasks.size();
        }
    }
}