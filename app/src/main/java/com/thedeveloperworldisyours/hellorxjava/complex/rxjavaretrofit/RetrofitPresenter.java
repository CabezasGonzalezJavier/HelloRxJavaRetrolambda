package com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit;

import android.support.annotation.NonNull;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.complex.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by javierg on 08/12/2016.
 */

public class RetrofitPresenter implements RetrofitContract.Presenter {

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    RetrofitContract.View mView;
    Observable<User> mObjectObservable;
    List<String> mGithubList;
    Retrofit mRetrofit;

    public RetrofitPresenter(@NonNull RetrofitContract.View view, @NonNull BaseSchedulerProvider baseSchedulerProvider) {
        this.mView = view;
        this.mSchedulerProvider = baseSchedulerProvider;
        mView.setPresenter(this);
    }

    @Override
    public void fetch() {

        mRetrofit = new Retrofit.Builder()
                .baseUrl(GithubService.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        getUserList();
        for (int i = 0; mGithubList.size() > i; i++) {
            call(mGithubList.get(i));
        }
    }

    public void call(String userString) {

        mObjectObservable = mRetrofit
                .create(GithubService.class)
                .getUserRx(userString)
                .subscribeOn(mSchedulerProvider.io())
                .observeOn(mSchedulerProvider.ui());

        mObjectObservable.subscribe(
                (User user) -> {
                    GithubUserList githubUser = new GithubUserList(user.getLogin(), user.getEventsUrl(), user.getPublicRepos());
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
//        mObjectObservable.cl
    }


}
