package com.sapronov.todolist;

import androidx.fragment.app.Fragment;

public class TaskActivity extends BaseActivity {

    @Override
    public Fragment loadFragment() {
        return new TaskFragment();
    }
}