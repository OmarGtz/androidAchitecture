package com.fds.mx.mvp_example.util;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiskIOThreadExecutor implements Executor {
    private final Executor mDiskIO;

    public DiskIOThreadExecutor() {
        this.mDiskIO = Executors.newSingleThreadExecutor();
    }

    @Override
    public void execute(@NonNull Runnable runnable) {

    }
}
