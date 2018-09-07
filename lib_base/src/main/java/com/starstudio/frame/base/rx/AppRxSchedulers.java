package com.starstudio.frame.base.rx;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hongsec on 17. 11. 13.
 */

public class AppRxSchedulers implements RxSchedulers {

    private static AppRxSchedulers appRxSchedulers;

    public static AppRxSchedulers getInstance() {
        if (appRxSchedulers == null) {
            synchronized (AppRxSchedulers.class) {
                if (appRxSchedulers == null) {
                    appRxSchedulers = new AppRxSchedulers();
                }
                return appRxSchedulers;
            }
        }
        return appRxSchedulers;
    }

    //库
    public static Executor backgroundExecutor = Executors.newCachedThreadPool();

    public static Scheduler BACKGROUND_SCHEDULERS = Schedulers.from(backgroundExecutor);

    //库
    public static Executor internetExecutor = Executors.newCachedThreadPool();

    public static Scheduler INTERNET_SCHEDULERS = Schedulers.from(internetExecutor);


    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @Override
    public Scheduler compute() {
        return Schedulers.computation();
    }

    @Override
    public Scheduler androidThread() {
        return AndroidSchedulers.mainThread();
    }

    @Override
    public Scheduler internet() {
        return INTERNET_SCHEDULERS;
    }

    @Override
    public Scheduler runOnBackground() {
        return BACKGROUND_SCHEDULERS;
    }
}
