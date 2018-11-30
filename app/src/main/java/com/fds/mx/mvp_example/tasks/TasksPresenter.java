/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.fds.mx.mvp_example.tasks;

import android.app.Activity;
import android.app.Instrumentation;

import com.fds.mx.mvp_example.addedittask.AddEditTaskActivity;
import com.fds.mx.mvp_example.data.TaskDataSource;
import com.fds.mx.mvp_example.data.TaskRepository;
import com.fds.mx.mvp_example.data.local.entity.TaskEntity;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Listens to user actions from the UI ({), retrieves the data and updates the
 * UI as required.
 */
public class TasksPresenter implements TasksContract.Presenter{
    private TaskRepository repository;
    private TasksContract.View taskView;
    private TasksFilterType filterType = TasksFilterType.ALL_TASKS;
    private boolean isFirtsLoad;


    public TasksPresenter(TaskRepository repository, TasksContract.View taskView) {
        this.repository = repository;
        this.taskView = taskView;
        taskView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {
        if(AddEditTaskActivity.REQUEST_ADD_TASK==requestCode&& Activity.RESULT_OK==resultCode){
            taskView.showSuccessfullySavedMessage();
        }
    }

    @Override
    public void loadTasks(boolean forceUpdate) {
        if(forceUpdate || isFirtsLoad){

        }
    }

    public void loadTasks(boolean forceUpdate, boolean showLoadingUI){
        if(showLoadingUI){
            taskView.setLoadingIndicator(true);
        }

        if(forceUpdate){
            repository.refreshTasks();
        }

        repository.getTasks(new TaskDataSource.GetTasksCallback() {
            @Override
            public void onTasksComplete(List<TaskEntity> tasks) {
                taskView.showTasks(tasks);
            }

            @Override
            public void onDataNoAvailable() {
                taskView.showLoadingTasksError();
            }
        });

    }

    @Override
    public void addNewTasks() {

    }

    @Override
    public void openTaskDetail(TaskEntity requestedTask) {

    }

    @Override
    public void completeTask(TaskEntity completedTask) {

    }

    @Override
    public void activateTask(TaskEntity activateTask) {

    }

    @Override
    public void clearCompleteTask() {

    }

    @Override
    public void setFiltering(TasksFilterType tasksFilterType) {

    }

    @Override
    public TasksFilterType getFiltering() {
        return null;
    }

    @Override
    public void start() {

    }
}
