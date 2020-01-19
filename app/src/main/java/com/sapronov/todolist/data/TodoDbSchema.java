package com.sapronov.todolist.data;

public class TodoDbSchema {

    public static final class TaskTable {

        public static final String NAME = "tasks";

        public static final class Cols {

            public static final String NAME = "name";
            public static final String DESC = "desc";
            public static final boolean CLOSED = false;
        }
    }
}
