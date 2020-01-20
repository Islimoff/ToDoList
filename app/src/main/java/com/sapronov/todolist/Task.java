package com.sapronov.todolist;

import java.util.Calendar;
import java.util.Date;

public class Task {

    private int id;
    private String name;
    private String desc;
    private Calendar create;
    private boolean closed;

    public Task(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        closed = false;
        create = Calendar.getInstance();
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean getClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Calendar getCreate() {
        return create;
    }

    public void editTask(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public int getId() {
        return id;
    }
}
