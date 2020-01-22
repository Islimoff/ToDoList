package com.sapronov.todolist;

import java.util.Calendar;

public class Task {

    private int id;
    private String name;
    private String desc;
    private Calendar create;
    private boolean closed;

    public Task(String name, String desc, int closed) {
        this.name = name;
        this.desc = desc;
        this.closed = closed != 0;
        create = Calendar.getInstance();
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public Calendar getCreate() {
        return create;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task exam = (Task) o;
        return id == exam.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", desc=" + desc +
                '}';
    }
}
