package com.sapronov.todolist.data;

import com.sapronov.todolist.model.Task;

public interface IStore {
    void add(Task task);

    int size();

    Task get(int index);
}
