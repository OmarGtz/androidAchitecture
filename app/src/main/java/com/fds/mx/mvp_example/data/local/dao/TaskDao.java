package com.fds.mx.mvp_example.data.local.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.fds.mx.mvp_example.data.local.entity.TaskEntity;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM TASK")
    List<TaskEntity> getTasks();

    @Query("SELECT * FROM TASK WHERE entryid = :id")
    TaskEntity getTaskById(String id);

    @Insert
    void InsertTask(TaskEntity task);

    @Update
    int updateTask(TaskEntity task);

    @Query("UPDATE TASK SET completed = :complete WHERE entryid = :id")
    void updateTaskComplete(String id, boolean complete);

    @Query("DELETE FROM TASK")
    void deleteTasks();

    @Query("DELETE FROM TASK WHERE entryid = :id")
    int deleteTaskById(String id);

    @Query("DELETE FROM TASK WHERE completed = 1")
    int deleteCOmpleteTask();

}
