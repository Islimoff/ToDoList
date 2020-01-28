package com.sapronov.todolist.activities;

import androidx.fragment.app.Fragment;

import com.sapronov.todolist.fragments.TaskListFragment;


public class MainActivity extends BaseActivity {
    @Override
    public Fragment loadFragment() {
        return new TaskListFragment();
    }
}
