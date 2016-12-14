package com.thedeveloperworldisyours.hellorxjava;

import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.SchedulerProvider;

/**
 * Created by javierg on 09/12/2016.
 */

public class Injection {

    public static BaseSchedulerProvider provideSchedulerProvider() {
        return SchedulerProvider.getInstance();
    }
}
