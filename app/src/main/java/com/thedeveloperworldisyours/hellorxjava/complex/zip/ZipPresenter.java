package com.thedeveloperworldisyours.hellorxjava.complex.zip;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.complex.User;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by javierg on 07/12/2016.
 */

public class ZipPresenter implements ZipContract.Presenter {

    @NonNull
    public ZipContract.View mView;

    public Observable<UserAndEvents> mCombined;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    public ZipPresenter(@NonNull ZipContract.View view, @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mView = view;
        this.mSchedulerProvider = schedulerProvider;
        mView.setPresenter(this);
    }

    @Override
    public void call(String userString) {
        Retrofit repo = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Observable<JsonObject> userObservable = repo
                .create(UserService.class)
                .getUser(userString)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui());

        Observable<JsonArray> eventsObservable = repo
                .create(EventsService.class)
                .listEvents(userString)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui());


        mCombined = Observable.zip(userObservable, eventsObservable, (JsonObject jsonObject, JsonArray jsonElements) -> {
            return new UserAndEvents(jsonObject, jsonElements);
        });

        mCombined.subscribe((UserAndEvents o) -> {
                    Gson gson = new Gson();
                    User user = gson.fromJson(o.user.toString(), User.class);
                    mView.show(user.getEventsUrl());
                },

                (Throwable e) ->
                        mView.showError()
                ,
                () -> {

                });
    }

    @Override
    public void subscribe() {
        call("Cabezas");
    }

    @Override
    public void unsubscribe() {
//        mSubscription.unsubscribe();
    }
}
