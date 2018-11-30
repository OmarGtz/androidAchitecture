package com.fds.mx.mvp_example.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.fds.mx.mvp_example.data.local.TaskLocalDataSource;
import com.fds.mx.mvp_example.data.local.entity.TaskEntity;
import com.fds.mx.mvp_example.data.nertwork.TasksRemoteDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static com.google.common.base.Preconditions.checkNotNull;

public class TaskRepository implements TaskDataSource {

    private static TaskRepository INSTANCE = null;
    private TaskLocalDataSource localDataSource;
    private TasksRemoteDataSource remoteDataSource;
    Map<String,TaskEntity> mCachedTask;
    boolean mCacheIsDirty = false;

    public TaskRepository(TaskLocalDataSource localDataSource, TasksRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    public static TaskRepository getInstance(TaskLocalDataSource localDataSource, TasksRemoteDataSource networkDataSource) {
        if(INSTANCE == null){
            INSTANCE = new TaskRepository(localDataSource,networkDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }

    @Override
    public void getTasks(final GetTasksCallback tasksCallback) {
        checkNotNull(tasksCallback);
        if(mCachedTask!=null && !mCacheIsDirty){
            tasksCallback.onTasksComplete(new ArrayList<TaskEntity>(mCachedTask.values()));
            return;
        }
        if(mCacheIsDirty){
            getTasksFromRemoteDatasource(tasksCallback);
        }else {
            localDataSource.getTasks(new GetTasksCallback() {
                @Override
                public void onTasksComplete(List<TaskEntity> tasks) {
                    refreshCache(tasks);
                    tasksCallback.onTasksComplete(tasks);
                }

                @Override
                public void onDataNoAvailable() {
                    getTasksFromRemoteDatasource(tasksCallback);
                }
            });
        }
    }

    public void getTasksFromRemoteDatasource(final GetTasksCallback callback){
        remoteDataSource.getTasks(new GetTasksCallback() {
            @Override
            public void onTasksComplete(List<TaskEntity> tasks) {
                refreshCache(tasks);
                refreshLocalDataSource(tasks);
                callback.onTasksComplete(tasks);
            }

            @Override
            public void onDataNoAvailable() {
                callback.onDataNoAvailable();
            }
        });

    }

    private void refreshCache(List<TaskEntity> tasks) {
        if (mCachedTask == null) {
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.clear();
        for (TaskEntity task : tasks) {
            mCachedTask.put(task.getId(), task);
        }
        mCacheIsDirty = false;
    }


    private void refreshLocalDataSource(List<TaskEntity> tasks) {
        localDataSource.deleteAllTasks();
        for (TaskEntity task : tasks) {
            localDataSource.saveTask(task);
        }
    }

    @Override
    public void getTask(final String taskId, final GetTaskCallback taskCallback) {
        checkNotNull(taskId);
        checkNotNull(taskCallback);
        TaskEntity taskCache= getTaskWithId(taskId);

        if(taskCache!=null){
            taskCallback.onTaskComplete(taskCache);
            return;
        }

        localDataSource.getTask(taskId, new GetTaskCallback() {
            @Override
            public void onTaskComplete(TaskEntity task) {
                if(mCachedTask==null){
                    mCachedTask = new LinkedHashMap<>();
                }
                mCachedTask.put(taskId,task);
                taskCallback.onTaskComplete(task);
            }

            @Override
            public void onDataNoAvailable() {
                remoteDataSource.getTask(taskId, new GetTaskCallback() {
                    @Override
                    public void onTaskComplete(TaskEntity task) {
                        if(mCachedTask==null){
                            mCachedTask = new LinkedHashMap<>();
                        }
                        mCachedTask.put(taskId,task);
                        taskCallback.onTaskComplete(task);
                    }

                    @Override
                    public void onDataNoAvailable() {
                        taskCallback.onDataNoAvailable();
                    }
                });
            }
        });
    }

    @Override
    public void saveTask(TaskEntity task) {
        checkNotNull(task);
        remoteDataSource.saveTask(task);
        localDataSource.saveTask(task);

        if(mCachedTask==null){
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.put(task.getId(),task);
    }

    @Override
    public void completeTask(TaskEntity task) {
        remoteDataSource.completeTask(task);
        localDataSource.completeTask(task);

        TaskEntity taskCompleted = new TaskEntity(task.getId(),task.getDescription(),task.getTitle(),true);

        if(mCachedTask==null){
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.put(taskCompleted.getId(),taskCompleted);
    }

    @Override
    public void completeTask(String taskId) {
        checkNotNull(taskId);
        completeTask(getTaskWithId(taskId));
    }

    @Override
    public void activateTask(TaskEntity task) {
        remoteDataSource.activateTask(task);
        localDataSource.activateTask(task);

        TaskEntity taskActivate = new TaskEntity(task.getId(),task.getDescription(),task.getTitle());

        if(mCachedTask == null){
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.put(task.getId(),taskActivate);
    }

    @Override
    public void activateTask(String taskId) {
         checkNotNull(taskId);
        activateTask(getTaskWithId(taskId));
    }

    @Override
    public void clearCompletedTasks() {
        Iterator<HashMap.Entry<String,TaskEntity>> iterator = mCachedTask.entrySet().iterator();
        while (iterator.hasNext()){
            HashMap.Entry<String,TaskEntity> entry = iterator.next();
            if(entry.getValue().isCompleted()){
                iterator.remove();
            }
        }
    }

    @Override
    public void refreshTasks() {
        mCacheIsDirty = true;
    }

    @Override
    public void deleteAllTasks() {
        remoteDataSource.deleteAllTasks();
        localDataSource.deleteAllTasks();
        if(mCachedTask == null){
            mCachedTask = new LinkedHashMap<>();
        }
        mCachedTask.clear();
    }

    @Override
    public void deleteTask(String taskId) {
        remoteDataSource.deleteTask(taskId);
        localDataSource.deleteTask(taskId);
        mCachedTask.remove(taskId);
    }

    @Nullable
    private TaskEntity getTaskWithId(@NonNull String id) {
        checkNotNull(id);
        if (mCachedTask == null || mCachedTask.isEmpty()) {
            return null;
        } else {
            return mCachedTask.get(id);
        }
    }
}
