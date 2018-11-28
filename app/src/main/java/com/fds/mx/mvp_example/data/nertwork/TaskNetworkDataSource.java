package com.fds.mx.mvp_example.data.nertwork;

import com.fds.mx.mvp_example.data.TaskDataSource;
import com.fds.mx.mvp_example.data.local.entity.TaskEntity;
import com.fds.mx.mvp_example.util.AppExecutors;

public class TaskNetworkDataSource implements TaskDataSource {

    AppExecutors appExecutors;


    @Override
    public void getTasks(GetTasksCallback tasksCallback) {

    }

    @Override
    public void getTask(String taskId, GetTaskCallback taskCallback) {

    }

    @Override
    public void saveTask(TaskEntity task) {

    }

    @Override
    public void completeTask(TaskEntity task) {

    }

    @Override
    public void completeTask(String TaskId) {

    }

    @Override
    public void activateTask(TaskEntity task) {

    }

    @Override
    public void activateTask(String taskId) {

    }

    @Override
    public void clearCompleteTask() {

    }

    @Override
    public void refreshTask() {

    }

    @Override
    public void deleteAllTask() {

    }

    @Override
    public void deleteTask(String taskId) {

    }
}
