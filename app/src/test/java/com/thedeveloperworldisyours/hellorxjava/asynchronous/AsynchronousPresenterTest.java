package com.thedeveloperworldisyours.hellorxjava.asynchronous;

import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;
import com.thedeveloperworldisyours.hellorxjava.simple.asynchronous.AsynchronousContract;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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

    @Mock
    Callable<List<String>> mCallable;


    private static List<String> sShowTV;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        sShowTV = new ArrayList<>();
        sShowTV.add("The Joy of Painting");
        sShowTV.add("The Simpsons");
        sShowTV.add("Futurama");
        sShowTV.add("Game of Thrones");
        sShowTV.add("Law And Order");

    }

    @Test
    public void successfulShowTV() throws Exception {

        when(mCallable.call()).thenReturn(sShowTV);

        Observable.fromCallable(mCallable)
                .subscribe((List<String> tvShows) -> mView.displayTvShows(tvShows),
                        (error) -> mView.errorTvShows(),
                        () -> {});

        verify(mView).displayTvShows(sShowTV);
        verify(mCallable).call();

    }

    @Test
    public void errorShowTV() throws Exception {

        Throwable error = new IllegalStateException();

        when(mCallable.call()).thenThrow(error);


        Observable.fromCallable(mCallable)
                .subscribe((List<String> tvShows) -> mView.displayTvShows(tvShows),
                        (errorSubscribe) -> mView.errorTvShows(),
                        () -> {});

        verify(mView).errorTvShows();
        verify(mCallable).call();

    }

}
