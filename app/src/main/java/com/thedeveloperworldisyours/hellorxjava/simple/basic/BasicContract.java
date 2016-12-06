package com.thedeveloperworldisyours.hellorxjava.simple.basic;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

import java.util.List;

/**
 * Created by javierg on 06/12/2016.
 */

public class BasicContract {

    public interface View extends BaseView<Presenter> {
        void configureLayout();
        void setInfo(List<String> colors);
    }

    interface Presenter extends BasePresenter {
        List<String> getColorList();
        void createObservable();
    }
}
