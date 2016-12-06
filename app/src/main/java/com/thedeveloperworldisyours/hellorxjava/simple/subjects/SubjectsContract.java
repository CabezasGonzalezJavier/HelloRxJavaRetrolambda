package com.thedeveloperworldisyours.hellorxjava.simple.subjects;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

/**
 * Created by javierg on 06/12/2016.
 */

public class SubjectsContract {

    public interface View extends BaseView<SubjectsContract.Presenter> {
        void setInfo(String info);
    }

    public interface Presenter extends BasePresenter{
        void createCounterEmitter();
        void onIncrementButtonClick();
    }
}
