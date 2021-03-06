package com.sapronov.todolist.data;

public class TodoDbSchema {

    public static final class TaskTable {

        public static final String NAME = "tasks";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String TITLE = "title";
            public static final String CLOSED = "closed";
            public static final String PHOTO_URI ="photoUri" ;
        }
    }
}
