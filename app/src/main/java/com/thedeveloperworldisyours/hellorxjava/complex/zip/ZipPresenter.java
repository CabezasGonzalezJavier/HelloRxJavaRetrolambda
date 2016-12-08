package com.thedeveloperworldisyours.hellorxjava.complex.zip;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.thedeveloperworldisyours.hellorxjava.complex.User;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by javierg on 07/12/2016.
 */

public class ZipPresenter implements ZipContract.Presenter {

    public ZipContract.View mView;

    public Observable<UserAndEvents> mCombined;
    private Subscription mSubscription;

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
                .create(UserService.class)
                .getUser("Cabezas")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());

        Observable<JsonArray> eventsObservable = repo
                .create(EventsService.class)
                .listEvents("Cabezas")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread());


        mCombined = Observable.zip(userObservable, eventsObservable, (JsonObject jsonObject, JsonArray jsonElements) -> {
            return new UserAndEvents(jsonObject, jsonElements);
        });
        mSubscription = mCombined.subscribe((UserAndEvents o) -> {
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
        call();
    }

    @Override
    public void unsubscribe() {
        mSubscription.unsubscribe();
    }
}
