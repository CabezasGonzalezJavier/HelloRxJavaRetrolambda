package com.thedeveloperworldisyours.hellorxjava.single;

import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;
import com.thedeveloperworldisyours.hellorxjava.simple.singles.SinglesContract;
import com.thedeveloperworldisyours.hellorxjava.simple.singles.SinglesPresenter;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by javierg on 15/12/2016.
 */

public class SinglePresenterTest {


    @Mock
    RestClient mRestClient;

    @Mock
    SinglesContract.View mView;

    SinglesPresenter mPresenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mPresenter = new SinglesPresenter(mView, mRestClient);
    }
}
