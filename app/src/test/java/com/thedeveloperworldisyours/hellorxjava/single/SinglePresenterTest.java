package com.thedeveloperworldisyours.hellorxjava.single;

import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;
import com.thedeveloperworldisyours.hellorxjava.simple.singles.SinglesContract;
import com.thedeveloperworldisyours.hellorxjava.simple.singles.SinglesPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 15/12/2016.
 */

public class SinglePresenterTest {

    @Mock
    Callable<List<String>> mCallable;

    @Mock
    RestClient mRestClient;

    @Mock
    SinglesContract.View mView;

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
    public void successful() throws Exception {

        when(mCallable.call()).thenReturn(sShowTV);
        Single.fromCallable(mCallable)
                .subscribe((List<String> tvShows) -> mView.displayTvShows(tvShows),
                        (Throwable error) ->  mView.displayErrorMessage());

        verify(mView).displayTvShows(sShowTV);
        verify(mCallable).call();

    }

    @Test
    public void fail() throws Exception {
        Throwable error = new IllegalStateException();

        when(mCallable.call()).thenThrow(error);

        Single.fromCallable(mCallable)
                .subscribe((List<String> tvShows) -> mView.displayTvShows(tvShows),
                        (Throwable errorSubscribe) ->  mView.displayErrorMessage());

        verify(mView).displayErrorMessage();
        verify(mCallable).call();
    }
}
