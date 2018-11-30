package com.fds.mx.mvp_example.data.local;

import android.arch.persistence.room.Update;
import android.net.IpSecManager;

import com.fds.mx.mvp_example.data.TaskDataSource;
import com.fds.mx.mvp_example.data.local.dao.TaskDao;
import com.fds.mx.mvp_example.data.local.entity.TaskEntity;
import com.fds.mx.mvp_example.util.AppExecutors;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import javax.annotation.CheckForNull;

public class TaskLocalDataSource implements TaskDataSource {

    private static volatile TaskLocalDataSource INSTANCE;
    AppExecutors appExecutors;
    private TaskDao taskDao;

    public TaskLocalDataSource(AppExecutors appExecutors, TaskDao taskDao) {
        this.appExecutors = appExecutors;
        this.taskDao = taskDao;
    }

    public static TaskLocalDataSource getInstance(AppExecutors appExecutors,TaskDao taskDao){
        if(INSTANCE == null){
            synchronized (TaskLocalDataSource.class){
                if(INSTANCE == null){
                    INSTANCE = new TaskLocalDataSource(appExecutors,taskDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getTasks(final GetTasksCallback tasksCallback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<TaskEntity> tasks = taskDao.getTasks();
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(tasks.isEmpty()){
                            tasksCallback.onDataNoAvailable();
                        }else {
                            tasksCallback.onTasksComplete(tasks);
                        }
                    }
                });

            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getTask(final String taskId, final GetTaskCallback taskCallback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final TaskEntity task = taskDao.getTaskById(taskId);
                appExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(task!=null){
                            taskCallback.onTaskComplete(task);
                        }else {
                            taskCallback.onDataNoAvailable();
                        }
                    }
                });
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveTask(final TaskEntity task) {
        checkNotNull(task);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                taskDao.InsertTask(task);
            }
        };

        appExecutors.diskIO().execute(saveRunnable);

    }

    @Override
    public void completeTask(final TaskEntity task) {
        Runnable updateRunable = new Runnable() {
            @Override
            public void run() {
                taskDao.updateTaskComplete(task.getId(),true);
            }
        };
        appExecutors.diskIO().execute(updateRunable);
    }

    @Override
    public void completeTask(String TaskId) {

    }

    @Override
    public void activateTask(final TaskEntity task) {
        Runnable activateRunnable = new Runnable() {
            @Override
            public void run() {
                taskDao.updateTaskComplete(task.getId(),false);
            }
        };
        appExecutors.diskIO().execute(activateRunnable);
    }

    @Override
    public void activateTask(String taskId) {

    }

    @Override
    public void clearCompletedTasks() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                taskDao.deleteCOmpleteTask();
            }
        };
        appExecutors.diskIO().execute(deleteRunnable);

    }

    @Override
    public void refreshTasks() {

    }

    @Override
    public void deleteAllTasks() {
        Runnable deleteAllRunnable = new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTasks();
            }
        };
        appExecutors.diskIO().execute(deleteAllRunnable);
    }

    @Override
    public void deleteTask(final String taskId) {
        Runnable deleteTaskRunnable = new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTaskById(taskId);
            }
        };
        appExecutors.diskIO().execute(deleteTaskRunnable);
    }
}