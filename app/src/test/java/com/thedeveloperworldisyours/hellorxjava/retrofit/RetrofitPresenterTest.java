package com.thedeveloperworldisyours.hellorxjava.retrofit;

import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.ImmediateSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit.GithubUserList;
import com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit.RetrofitContract;
import com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit.RetrofitPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 16/12/2016.
 */

public class RetrofitPresenterTest {

    private RetrofitPresenter mRetrofitPresenter;
    private BaseSchedulerProvider mSchedulerProvider;
    private String mUser;

    private GithubUserList mGithubUser;

    @Mock
    private RetrofitContract.View mView;

    @Before
    public void setUp(){

        MockitoAnnotations.initMocks(this);

        mSchedulerProvider = new ImmediateSchedulerProvider();
        mRetrofitPresenter = new RetrofitPresenter(mView, mSchedulerProvider);

    }

    @Test
    public void successfulFetchData() {

        mUser = "google";

        mGithubUser = new GithubUserList("google", "", 12);

        mRetrofitPresenter.call(mUser);


        verify(mView).showInfo(mGithubUser);

    }
}
