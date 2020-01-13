package com.sapronov.todolist;

import java.util.ArrayList;
import java.util.List;

public class Store {

    private List<Task> tasks=new ArrayList<>();
    private static final Store INST=new Store();

    private Store(){}

    public static Store getStore(){
        return INST;
    }

    public void addTask(Task task){
        tasks.add(task);
    }

    public Task get(int index){
        return tasks.get(index);
    }

    public int size(){
        return tasks.size();
    }

    public void deleteAllTasks(){
        tasks.clear();
    }

    public List<Task> getAll(){
        return tasks;
    }
}
