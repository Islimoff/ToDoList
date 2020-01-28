package com.sapronov.todolist.fragments;

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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sapronov.todolist.R;
import com.sapronov.todolist.data.SqlStore;
import com.sapronov.todolist.model.Task;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TaskListFragment extends Fragment implements DeleteTasksFragment.ConfirmDeleteTasksListener {

    private RecyclerView recycler;

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
        updateUI();
        return view;
    }

    private void updateUI() {
        List<Task> tasks = SqlStore.getStore(getContext()).getAll();
        this.recycler.setAdapter(new TaskAdapter(tasks));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager fm = getFragmentManager();
        switch (item.getItemId()) {
            case R.id.add_item:
                fm.beginTransaction()
                        .replace(R.id.content, new TaskFormFragment())
                        .addToBackStack(null)
                        .commit();
                return true;
            case R.id.delete_item:
                DialogFragment dialog = new DeleteTasksFragment(this);
                dialog.show(fm, "dialog_tag");
                updateUI();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPositiveDialogClick(DialogFragment dialog) {
        SqlStore.getStore(getContext()).deleteAll();
        updateUI();
    }

    @Override
    public void onNegativeDialogClick(DialogFragment dialog) {
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
                bundle.putInt("closed", task.isClosed() ? 1 : 0);
                bundle.putString("name", task.getName());
                bundle.putString("desc", task.getDesc());
                fragment.setArguments(bundle);
                fm.beginTransaction()
                        .replace(R.id.content, fragment)
                        .addToBackStack(null)
                        .commit();
            });
            CheckBox done = holder.view.findViewById(R.id.checkBox);
            done.setChecked(task.isClosed());
            done.setOnCheckedChangeListener(((view, isClosed) -> {
                task.setClosed(isClosed);
                SqlStore.getStore(getContext()).update(task);
            }));


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