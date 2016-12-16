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

import static org.mockito.Mockito.verify;

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
        sShowTV.add("The Joy of Painting");
        sShowTV.add("The Simpsons");
        sShowTV.add("Futurama");
        sShowTV.add("Rick & Morty");
        sShowTV.add("The X-Files");
        sShowTV.add("Star Trek: The Next Generation");
        sShowTV.add("Archer");
        sShowTV.add("30 Rock");
        sShowTV.add("Bob's Burgers");
        sShowTV.add("Breaking Bad");
        sShowTV.add("Parks and Recreation");
        sShowTV.add("House of Cards");
        sShowTV.add("Game of Thrones");
        sShowTV.add("Law And Order");

        mSchedulerProvider = new ImmediateSchedulerProvider();

        mPresenter = new AsynchronousPresenter(mRestClient, mSchedulerProvider, mView);
    }

    @Test
    public void successful() {
//        mRestClient.getFavoriteTvShows();
        mPresenter.getList();
        mPresenter.createObservable();

//        setShowTVAvailable(sShowTV);
        verify(mView).displayTvShows(sShowTV);
    }

//    @Test
//    public void error(){
//        mPresenter.subscribe();
//
//        setShowTVNotAvailable();
//        verify(mView).errorTvShows();
//
//    }
//
//    public void setShowTVAvailable(List<String> showTV) {
//        when(mPresenter.getList()).thenReturn(Observable.just(showTV));
//    }
//
//    public void setShowTVNotAvailable(){
//        when(mPresenter.getList()).thenReturn(Observable.error(new Exception()));
//    }
}
