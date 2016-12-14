package com.thedeveloperworldisyours.hellorxjava.Utils.scheduler;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by javierg on 09/12/2016.
 */

public class SchedulerProvider implements BaseSchedulerProvider {

    private static SchedulerProvider sInstance;

    private SchedulerProvider () {

    }

    public static synchronized SchedulerProvider getInstance() {
        if (sInstance == null) {
            sInstance = new SchedulerProvider();
        }

        return sInstance;
    }

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @NonNull
    @Override
    public Scheduler io() {
        return Schedulers.io();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}
