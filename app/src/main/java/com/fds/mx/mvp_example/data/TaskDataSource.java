package com.fds.mx.mvp_example.data;

import com.fds.mx.mvp_example.data.local.entity.TaskEntity;

import java.util.List;

public interface TaskDataSource {

    interface GetTasksCallback{
        void onTasksComplete(List<TaskEntity> tasks);
        void onDataNoAvailable();
    }

    interface GetTaskCallback{
        void onTaskComplete(TaskEntity task);
        void onDataNoAvailable();
    }

    void getTasks(GetTasksCallback tasksCallback);
    void getTask(String taskId,GetTaskCallback taskCallback);
    void saveTask(TaskEntity task);
    void completeTask(TaskEntity task);
    void completeTask(String TaskId);
    void activateTask(TaskEntity task);
    void activateTask(String taskId);
    void clearCompleteTask();
    void refreshTask();
    void deleteAllTask();
    void deleteTask(String taskId);
}