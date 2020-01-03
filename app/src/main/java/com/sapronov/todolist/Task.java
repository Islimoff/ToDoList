package com.sapronov.todolist;

import java.util.Date;

public class Task {

    private String name;
    private String desc;
    private Date create;
    private Date closed;

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public Date getCreate() {
        return create;
    }

    public Date getClosed() {
        return closed;
    }
}
