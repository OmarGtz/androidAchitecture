package com.fds.mx.mvp_example.data;

import com.fds.mx.mvp_example.data.local.TaskLocalDataSource;

public class TaskRepository {

    private static TaskRepository INSTANCE = null;
    private TaskLocalDataSource localDataSource;


    public TaskRepository(TaskLocalDataSource localDataSource) {
        this.localDataSource = localDataSource;
    }

    public TaskRepository(){

    }

    public static TaskRepository getInstance() {
        return INSTANCE;
    }

}
