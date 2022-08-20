package com.dicoding.tourismapp.core.utils;

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;

/**
 * Created by Budiliauw87 on 2020-12-30.
 * liautech.com
 * Budiliauw87@gmail.com
 */
public class AppExecutors {

    private final Executor diskIO;
    private final Executor networkIO;
    private final Executor mainThread;

    private static final int THREAD_COUNT = 3;

    @Inject
    public AppExecutors() {
        this(Executors.newSingleThreadExecutor(),
                Executors.newFixedThreadPool(THREAD_COUNT),
                new MainThreadExecutor()
        );
    }
    public AppExecutors(Executor diskIO, Executor networkIO, Executor mainThread) {
        this.diskIO = diskIO;
        this.networkIO = networkIO;
        this.mainThread = mainThread;
    }
    public Executor diskIO() {
        return diskIO;
    }

    public Executor networkIO() {
        return networkIO;
    }

    public Executor mainThread() {
        return mainThread;
    }
    private static class MainThreadExecutor implements Executor{
        private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable runnable) {
            mainThreadHandler.post(runnable);
        }
    }
}
