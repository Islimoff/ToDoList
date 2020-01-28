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
        Cursor cursor = store.query(TaskTable.NAME,
                null, "id = ?",
                new String[]{String.valueOf(index)},
                null, null, null);
        cursor.moveToFirst();
        Task task = new Task(cursor.getString(cursor.getColumnIndex(TaskTable.Cols.NAME))
                , cursor.getString(cursor.getColumnIndex(TaskTable.Cols.TITLE)),
                cursor.getInt(cursor.getColumnIndex(TaskTable.Cols.CLOSED)));
        task.setId(cursor.getInt(cursor.getColumnIndex(TaskTable.Cols.ID)));
        cursor.moveToNext();
        cursor.close();
        return task;
    }

    public void update(Task task) {
        store.update(TaskTable.NAME, getContentValues(task),
                "id = ?", new String[]{String.valueOf(task.getId())});
    }

    public void deleteAll() {
        store.delete(TaskTable.NAME, null, null);
    }

    public List<Task> getAll() {
        List<Task> tasks = new ArrayList<>();
        Cursor cursor = this.store.query(
                TaskTable.NAME,
                null, null, null,
                null, null, null
        );
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = new Task(cursor.getString(cursor.getColumnIndex(TaskTable.Cols.NAME))
                    , cursor.getString(cursor.getColumnIndex(TaskTable.Cols.TITLE)),
                    cursor.getInt(cursor.getColumnIndex(TaskTable.Cols.CLOSED)));
            task.setId(cursor.getInt(cursor.getColumnIndex(TaskTable.Cols.ID)));
            tasks.add(task);

            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }

    private ContentValues getContentValues(Task task) {
        ContentValues values = new ContentValues();
        values.put(TaskTable.Cols.NAME, task.getName());
        values.put(TaskTable.Cols.TITLE, task.getDesc());
        values.put(TaskTable.Cols.CLOSED, task.isClosed() ? 1 : 0);
        return values;
    }
}
