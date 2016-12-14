package com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

/**
 * Created by javierg on 08/12/2016.
 */

public class RetrofitContract {

    public interface View extends BaseView<Presenter> {
        void configureLayout();
        void showInfo(GithubUserList user);
        void showError(String error);
    }

    public interface Presenter extends BasePresenter{
        void fetch();

        void getUserList();
    }
}
