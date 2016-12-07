package com.thedeveloperworldisyours.hellorxjava.complex;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

/**
 * Created by javierg on 07/12/2016.
 */

public class ZipContract {

    public interface View extends BaseView<Presenter> {
        void show(UserAndEvents o);
    }

    public interface Presenter extends BasePresenter {
        void call();

        void finishCall();
    }
}
