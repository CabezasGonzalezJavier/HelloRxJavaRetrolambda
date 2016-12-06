package com.thedeveloperworldisyours.hellorxjava.simple.singles;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

import java.util.List;

/**
 * Created by javierg on 06/12/2016.
 */

public class SinglesContract {

    public interface View extends BaseView<Presenter> {
        void displayTvShows(List<String> tvShowsList);
        void displayErrorMessage();
    }

    public interface Presenter extends BasePresenter {
        void createSingle();
    }
}
