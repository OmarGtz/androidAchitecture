package com.fds.mx.mvp_example.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.fds.mx.mvp_example.data.local.dao.TaskDao;
import com.fds.mx.mvp_example.data.local.entity.TaskEntity;

@Database(entities = {TaskEntity.class},version = 1)
public abstract class TaskDB extends RoomDatabase {

    private static TaskDB INSTANCE;
    private final static String DB_NAME= "task_db";
    public abstract TaskDao taskDao();


    public static TaskDB getDB(Context context){
        synchronized (TaskDB.class){
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(context,TaskDB.class,DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
    }
}
