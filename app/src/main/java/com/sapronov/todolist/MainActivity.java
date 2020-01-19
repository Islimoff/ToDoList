package com.sapronov.todolist;

import androidx.fragment.app.Fragment;


public class MainActivity extends BaseActivity  {
    @Override
    public Fragment loadFragment() {
        return new TaskListFragment();
    }
}
