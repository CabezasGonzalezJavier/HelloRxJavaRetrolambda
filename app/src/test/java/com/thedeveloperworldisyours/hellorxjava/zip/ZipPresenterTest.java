package com.thedeveloperworldisyours.hellorxjava.zip;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.ImmediateSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.complex.zip.UserAndEvents;
import com.thedeveloperworldisyours.hellorxjava.complex.zip.UserService;
import com.thedeveloperworldisyours.hellorxjava.complex.zip.ZipContract;
import com.thedeveloperworldisyours.hellorxjava.complex.zip.ZipPresenter;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        JSONObject jsonObj = null;
        try {

            String stringJson = "{\"login\":\"cabezas\",\"id\":248344,\"avatar_url\":\"https://avatars.githubusercontent.com/u/248344?v=3\",\"gravatar_id\":\"\",\"url\":\"https://api.github.com/users/cabezas\",\"html_url\":\"https://github.com/cabezas\",\"followers_url\":\"https://api.github.com/users/cabezas/followers\",\"following_url\":\"https://api.github.com/users/cabezas/following{/other_user}\",\"gists_url\":\"https://api.github.com/users/cabezas/gists{/gist_id}\",\"starred_url\":\"https://api.github.com/users/cabezas/starred{/owner}{/repo}\",\"subscriptions_url\":\"https://api.github.com/users/cabezas/subscriptions\",\"organizations_url\":\"https://api.github.com/users/cabezas/orgs\",\"repos_url\":\"https://api.github.com/users/cabezas/repos\",\"events_url\":\"https://api.github.com/users/cabezas/events{/privacy}\",\"received_events_url\":\"https://api.github.com/users/cabezas/received_events\",\"type\":\"User\",\"site_admin\":false,\"name\":\"fcabezasm\",\"company\":\"Gatorella\",\"blog\":\"facebook.com/gatosdelavega\",\"location\":null,\"email\":\"fcabezasm@gmail.com\",\"hireable\":null,\"bio\":\"ola k ase\",\"public_repos\":0,\"public_gists\":0,\"followers\":0,\"following\":0,\"created_at\":\"2010-04-20T16:58:09Z\",\"updated_at\":\"2016-12-11T16:05:44Z\"}";
            jsonObj = new JSONObject(stringJson);

            JsonParser jsonParser = new JsonParser();
            mGsonObject = (JsonObject) jsonParser.parse(jsonObj.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }


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
