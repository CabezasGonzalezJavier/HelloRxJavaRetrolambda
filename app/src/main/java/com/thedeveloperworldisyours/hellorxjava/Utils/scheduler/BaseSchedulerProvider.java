package com.thedeveloperworldisyours.hellorxjava.Utils.scheduler;

import android.support.annotation.NonNull;

import io.reactivex.Scheduler;


/**
 * Created by javierg on 09/12/2016.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
