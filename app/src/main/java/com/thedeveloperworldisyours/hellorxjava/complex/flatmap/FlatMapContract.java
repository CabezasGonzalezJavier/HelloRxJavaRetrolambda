package com.thedeveloperworldisyours.hellorxjava.complex.flatmap;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

/**
 * Created by javierg on 13/12/2016.
 */

public class FlatMapContract {

    public interface View extends BaseView<Presenter> {

        void showToast(String message);
        void debugLog(String message);
        void printFlatMapResult(String result);
        void printConcatMapResult(String result);
        void populateData();
    }

    public interface Presenter extends BasePresenter {
        String populateData();
        void flatMap();
        void concat();
    }
}
