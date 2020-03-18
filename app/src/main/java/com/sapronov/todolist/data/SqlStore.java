package com.sapronov.todolist.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.sapronov.todolist.model.Task;
import com.sapronov.todolist.data.TodoDbSchema.TaskTable;

import java.util.ArrayList;
import java.util.List;

public class SqlStore {

    private static SqlStore INST;
    private Context context;
    private SQLiteDatabase store;

    private SqlStore(Context context) {
        this.context = context.getApplicationContext();
        this.store = new TodoBaseHelper(context).getWritableDatabase();
    }

    public static SqlStore getStore(Context context) {
        if (INST == null) {
            INST = new SqlStore(context);
        }
        return INST;
    }

    public void add(Task task) {
        store.insert(TaskTable.NAME, null, getContentValues(task));
    }

    public Task get(int index) {
        Cursor cursor = null;
        Task task = null;
        try {
            cursor = store.query(TaskTable.NAME,
                    null, "id = ?",
                    new String[]{String.valueOf(index)},
                    null, null, null);
            cursor.moveToFirst();
            task = getTask(cursor);
            cursor.moveToNext();
        } finally {
            cursor.close();
        }
        return task;
    }

    public void update(Task task) {
        store.update(TaskTable.NAME, getContentValues(task),
                "id = ?", new String[]{String.valueOf(task.getId())});
    }

    public void deleteAll() {
        store.delete(TaskTable.NAME, null, null);
    }

    public Task getById(int id) {
        Cursor cursor = null;
        Task task = null;
        try {
            cursor = store.query(
                    TaskTable.NAME,
                    null, "id = " + id, null,
                    null, null, null
            );
            cursor.moveToFirst();
            task = getTask(cursor);
        } finally {
            cursor.close();
        }
        return task;
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = null;
        try {
            cursor = this.store.query(
                    TaskTable.NAME,
                    null, null, null,
                    null, null, null
            );
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                tasks.add(getTask(cursor));
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return tasks;
    }

    private Task getTask(Cursor cursor) {
        Task task = new Task(cursor.getString(cursor.getColumnIndex(TaskTable.Cols.NAME))
                , cursor.getString(cursor.getColumnIndex(TaskTable.Cols.TITLE))
                , cursor.getString(cursor.getColumnIndex(TaskTable.Cols.PHOTO_URI)),
                cursor.getInt(cursor.getColumnIndex(TaskTable.Cols.CLOSED)));
        task.setId(cursor.getInt(cursor.getColumnIndex(TaskTable.Cols.ID)));
        return task;
    }

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.NAME, task.getName());
        values.put(TaskTable.Cols.TITLE, task.getDesc());
        values.put(TaskTable.Cols.PHOTO_URI, task.getPhotoUri());
        values.put(TaskTable.Cols.CLOSED, task.isClosed() ? 1 : 0);
        return values;
    }
}
