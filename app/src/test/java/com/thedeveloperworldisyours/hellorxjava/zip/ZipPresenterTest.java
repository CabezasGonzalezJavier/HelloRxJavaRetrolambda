package com.thedeveloperworldisyours.hellorxjava.zip;

import com.google.gson.JsonObject;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.ImmediateSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.complex.zip.UserAndEvents;
import com.thedeveloperworldisyours.hellorxjava.complex.zip.UserService;
import com.thedeveloperworldisyours.hellorxjava.complex.zip.ZipContract;
import com.thedeveloperworldisyours.hellorxjava.complex.zip.ZipPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/**
 * Created by javierg on 15/12/2016.
 */

public class ZipPresenterTest {

    @Mock
    private UserAndEvents mUserAndEvents;

    @Mock
    private UserService userService;

    @Mock
    private ZipContract.View mView;

    private String mUser;

    private JsonObject mGsonObject;

    private BaseSchedulerProvider mSchedulerProvider;

    private ZipPresenter mPresenter;

    @Before
    public void setUpZip() {
        MockitoAnnotations.initMocks(this);

        mSchedulerProvider = new ImmediateSchedulerProvider();

        mUser = "";

        mPresenter = new ZipPresenter(mView, mSchedulerProvider);
    }

    @Test
    public void successfulGetData() {

        mUser = "Cabezas";
        mPresenter.call(mUser);

        verify(mView).show("https://api.github.com/users/cabezas/events{/privacy}");

    }

    @Test
    public void loadFail() {

        mUser = "";

        mPresenter.call(mUser);


        verify(mView).showError();

    }

}
