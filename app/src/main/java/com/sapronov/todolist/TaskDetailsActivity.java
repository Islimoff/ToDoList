package com.sapronov.todolist;

import androidx.fragment.app.Fragment;

public class TaskDetailsActivity extends BaseActivity {

    @Override
    public Fragment loadFragment() {
        return new TaskDetailsFragment();
    }
}
