package com.thedeveloperworldisyours.hellorxjava.simple.asynchronous;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

import java.util.List;

/**
 * Created by javierg on 06/12/2016.
 */

public class AsynchronousContract {

    public interface View extends BaseView<Presenter> {
        void displayTvShows(List<String> tvShows);
    }

    interface Presenter extends BasePresenter {
        void createObservable();
    }

}
