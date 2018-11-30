package com.fds.mx.mvp_example.tasks;

import com.fds.mx.mvp_example.base.BasePresenter;
import com.fds.mx.mvp_example.base.BaseView;
import com.fds.mx.mvp_example.data.local.entity.TaskEntity;

import java.util.List;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface TasksContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showTasks(List<TaskEntity> tasks);
        void showAddTask();
        void showTaskDetailsUi(String taskId);
        void showTaskMarkedComplete();
        void showTaskMarkedActive();
        void showCompletedTasksCleared();
        void showLoadingTasksError();
        void showNoTasks();
        void showActiveFilterLabel();
        void showCompletedFilterLabel();
        void showAllFilterLabel();
        void showNoActiveTasks();
        void showNoCompletedTasks();
        void showSuccessfullySavedMessage();
        boolean isActive();
        void showFilteringPopUpMenu();

    }

    public interface Presenter extends BasePresenter{
        void result(int requestCode, int resultCode);
        void loadTasks(boolean forceUpdate);
        void addNewTasks();
        void openTaskDetail(TaskEntity requestedTask);
        void completeTask(TaskEntity completedTask);
        void activateTask(TaskEntity activateTask);
        void clearCompleteTask();
        void setFiltering(TasksFilterType tasksFilterType);
        TasksFilterType getFiltering();
    }
}