package com.sapronov.todolist;

import androidx.fragment.app.Fragment;

public class TaskFormActivity extends BaseActivity {

    @Override
    public Fragment loadFragment() {
        return new TaskFormFragment();
    }
}
