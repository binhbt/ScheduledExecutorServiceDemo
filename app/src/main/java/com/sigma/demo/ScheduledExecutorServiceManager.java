package com.sigma.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by t430 on 7/2/2018.
 */

public class ScheduledExecutorServiceManager {
    private static final int DEFAULT_THREAD_POOL_SIZE = 4;
    private ScheduledExecutorService mScheduler =
            Executors.newScheduledThreadPool(DEFAULT_THREAD_POOL_SIZE);
    private List<Future> mRunningTaskList = new ArrayList<>();
    private static volatile ScheduledExecutorServiceManager mInstance;
    //Create singleton object with thread-safe
    public static ScheduledExecutorServiceManager getmInstance(){
        ScheduledExecutorServiceManager localRef = mInstance;
            if (localRef == null) {
                synchronized (ScheduledExecutorServiceManager.class) {
                    localRef = mInstance;
                    if (localRef == null) {
                        localRef = mInstance = new ScheduledExecutorServiceManager();
                    }
                }
            }
            return localRef;
    }
    public void cancelAllTasks() {
        synchronized (this) {
            for (Future task : mRunningTaskList) {
                if (!task.isDone()) {
                    task.cancel(true);
                }
            }
            mRunningTaskList.clear();
        }
    }
    public void addNewThreadWithFixedRate(int seconds, Runnable runnable){
        Future furture = mScheduler.scheduleAtFixedRate(runnable, 0, seconds, TimeUnit.SECONDS);
        mRunningTaskList.add(furture);
    }
}
