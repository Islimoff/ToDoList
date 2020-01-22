package com.sapronov.todolist.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TodoBaseHelper extends SQLiteOpenHelper {

    public static final String DB = "tasks.db";
    public static final int VERSION = 1;

    public TodoBaseHelper(Context context) {
        super(context, DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + TodoDbSchema.TaskTable.NAME + " (" +
                        TodoDbSchema.TaskTable.Cols.ID +
                        "integer primary key autoincrement, " +
                        TodoDbSchema.TaskTable.Cols.NAME + ", " +
                        TodoDbSchema.TaskTable.Cols.TITLE + ", " +
                        TodoDbSchema.TaskTable.Cols.CLOSED + " " +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
