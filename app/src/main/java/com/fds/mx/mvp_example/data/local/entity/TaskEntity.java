package com.fds.mx.mvp_example.data.local.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.common.base.Strings;

import java.util.UUID;

@Entity(tableName = "task")
public final class TaskEntity{

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "entryid")
    private final String mId;
    @NonNull
    @ColumnInfo(name = "title")
    private final String mTitle;
    @ColumnInfo(name = "description")
    @NonNull
    private final String mDescription;
    @ColumnInfo(name = "completed")
    @NonNull
    private final boolean mCompleted;

    @NonNull
    public String getId() {
        return mId;
    }

    @NonNull
    public String getTitle() {
        return mTitle;
    }

    @NonNull
    public String getDescription() {
        return mDescription;
    }

    public TaskEntity(@NonNull String mId, @NonNull String mTitle, @NonNull String mDescription, @NonNull boolean mCompleted) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mCompleted = mCompleted;
    }

    @Ignore
    public TaskEntity(@NonNull String mTitle, @NonNull String mDescription) {
        this(UUID.randomUUID().toString(),mTitle,mDescription,false);
    }

    @Ignore
    public TaskEntity(@NonNull String mId, @NonNull String mTitle, @NonNull String mDescription) {
        this(mId,mTitle,mDescription,false);
    }

    @Ignore
    public TaskEntity(@NonNull String mTitle, @NonNull String mDescription, @NonNull boolean mCompleted) {
        this(UUID.randomUUID().toString(),mTitle,mDescription,mCompleted);
    }


    @NonNull
    public boolean isCompleted() {
        return mCompleted;
    }

    public String getTitleForList(){
        if(!Strings.isNullOrEmpty(mTitle)){
            return mTitle;
        }else {
            return mDescription;
        }
    }

    public boolean isEmpty(){
        return Strings.isNullOrEmpty(mTitle)&&
                Strings.isNullOrEmpty(mDescription);
    }
}