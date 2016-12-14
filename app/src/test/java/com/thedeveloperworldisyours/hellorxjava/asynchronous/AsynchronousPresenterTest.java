package com.thedeveloperworldisyours.hellorxjava.asynchronous;

import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.ImmediateSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;
import com.thedeveloperworldisyours.hellorxjava.simple.asynchronous.AsynchronousContract;
import com.thedeveloperworldisyours.hellorxjava.simple.asynchronous.AsynchronousPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 09/12/2016.
 */

public class AsynchronousPresenterTest {

    @Mock
    private AsynchronousContract.View mView;

    @Mock
    private RestClient mRestClient;

    private AsynchronousPresenter mPresenter;

    private static List<String> sShowTV;

    private BaseSchedulerProvider mSchedulerProvider;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        sShowTV = new ArrayList<>();
        sShowTV.add(0, "String");
        sShowTV.add(1, "String1");
        sShowTV.add(2, "String2");

        mSchedulerProvider = new ImmediateSchedulerProvider();

        mPresenter = new AsynchronousPresenter(mRestClient, mSchedulerProvider, mView);
    }

    @Test
    public void successful() {
        mPresenter.subscribe();

        setShowTVAvailable(sShowTV);
        verify(mView).displayTvShows(sShowTV);
    }

    @Test
    public void error(){
        mPresenter.subscribe();

        setShowTVNotAvailable();
        verify(mView).errorTvShows();

    }

    public void setShowTVAvailable(List<String> showTV) {
        when(mPresenter.getList()).thenReturn(Observable.just(showTV));
    }

    public void setShowTVNotAvailable(){
        when(mPresenter.getList()).thenReturn(Observable.error(new Exception()));
    }
}
