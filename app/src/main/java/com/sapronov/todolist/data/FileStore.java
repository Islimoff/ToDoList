package com.sapronov.todolist.data;

import android.content.Context;

import com.sapronov.todolist.model.Task;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Calendar;

public class FileStore implements IStore {

    private static IStore instance;
    private int counter = 0;
    private Context context;

    private FileStore(Context context) {
        this.context = context;
    }

    public static IStore getInstance(Context context) {
        if (instance == null) {
            instance = new FileStore(context);
        }
        return instance;
    }

    @Override
    public void add(Task task) {
        File file = new File(context.getFilesDir(), (counter++) + ".txt");
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            out.println(task.getName());
            out.println(task.getCreate().getTimeInMillis());
            out.println(task.isClosed());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        return context.getFilesDir().listFiles().length;
    }

    @Override
    public Task get(int index) {
        Task task = new Task(null, null,null, 0);
        File file = new File(context.getFilesDir(), index + ".txt");
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            task.setName(in.readLine());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(Long.parseLong(in.readLine()));
            task.setCreate(cal);
            task.setClosed(Boolean.parseBoolean(in.readLine()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return task;
    }
}