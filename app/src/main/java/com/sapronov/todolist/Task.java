package com.sapronov.todolist;

import java.util.Calendar;
import java.util.Date;

public class Task {

    private String name;
    private String desc;
    private Calendar create;
    private Date closed;

    public Task(String name, String desc) {
        this.name = name;
        this.desc = desc;
        create=Calendar.getInstance();
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Date getClosed() {
        return closed;
    }

    public void setClosed(Date closed) {
        this.closed = closed;
    }

    public Calendar getCreate() {
        return create;
    }
}
