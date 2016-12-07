package com.thedeveloperworldisyours.hellorxjava.complex;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by javierg on 07/12/2016.
 */

public class ZipPresenter implements ZipContract.Presenter {

    public ZipContract.View mView;

    public Observable<UserAndEvents> mCombined;

    public ZipPresenter(ZipContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public void call() {
        Retrofit repo = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        Observable<JsonObject> userObservable = repo
                .create(GitHubUser.class)
                .getUser("Cabezas")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<JsonArray> eventsObservable = repo
                .create(GitHubEvents.class)
                .listEvents("Cabezas")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


        mCombined = Observable.zip(userObservable, eventsObservable, (JsonObject jsonObject, JsonArray jsonElements) -> {
            return new UserAndEvents(jsonObject, jsonElements);
        });
    }

    @Override
    public void finishCall() {
        mCombined.subscribe((UserAndEvents o) -> {
            mView.show(o);
                },

                (Throwable e) -> {

                },
                () -> {


                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
