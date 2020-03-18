package com.sapronov.todolist.model;

import java.util.Calendar;

public class Task {

    private int id;
    private String name;
    private String desc;
    private Calendar create;
    private String photoUri;
    private boolean closed;

    public Task(String name, String desc,String photoUri, int closed) {
        this.name = name;
        this.desc = desc;
        this.photoUri=photoUri;
        this.closed = closed != 0;
        this.create = Calendar.getInstance();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setCreate(Calendar create) {
        this.create = create;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return this.id == task.id && this.name.equals(task.name) &&
                this.desc.equals(task.desc) && this.create.equals(task.create);
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
