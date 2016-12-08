package com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit;

import android.util.Log;

import com.thedeveloperworldisyours.hellorxjava.complex.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by javierg on 08/12/2016.
 */

public class RetrofitPresenter implements RetrofitContract.Presenter {

    RetrofitContract.View mView;
    Observable<User> mObjectObservable;
    List<String> mGithubList;
    Retrofit mRetrofit;

    public RetrofitPresenter(RetrofitContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void fetch() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(GithubService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        getUserList();
        for (int i =0; mGithubList.size()>i; i++) {
            call(mGithubList.get(i));
        }
    }

    public void call(String userString) {
        mObjectObservable = mRetrofit
                .create(GithubService.class)
                .getUser(userString)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        mObjectObservable.subscribe(
                (User user) -> {
                    GithubUserList githubUser = new GithubUserList( user.getLogin(), user.getEventsUrl(),user.getPublicRepos());
                    mView.showInfo(githubUser);
                },
                (Throwable error) ->
                        mView.showError(error.toString())
                ,
                () -> {
                });

    }

    @Override
    public void getUserList() {
        mGithubList = new ArrayList<String>() {{
            add("linkedin");
            add("tumblr");
            add("square");
            add("google");
            add("stripe");
            add("angular");
            add("facebook");
            add("rails");
        }};
    }

    @Override
    public void subscribe() {
//        fetch();
    }

    @Override
    public void unsubscribe() {
    }


}
