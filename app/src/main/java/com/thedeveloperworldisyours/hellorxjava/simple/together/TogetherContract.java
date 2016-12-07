package com.thedeveloperworldisyours.hellorxjava.simple.together;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

import java.util.List;

/**
 * Created by javierg on 06/12/2016.
 */

public class TogetherContract {

    public interface View extends BaseView<Presenter> {
        void showNoSearchResults();
        void showSearchResults(List<String> cities);
        void listenToSearchInput();
    }
    public interface Presenter extends BasePresenter {
        void createObservables();
        void handleSearchResults(List<String> cities);
        void next(CharSequence s);
    }
}
