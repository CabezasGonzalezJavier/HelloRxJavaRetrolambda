package com.thedeveloperworldisyours.hellorxjava.simple.asynchronous;

import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by javierg on 06/12/2016.
 */

public class AsynchronousPresenter implements AsynchronousContract.Presenter {

    private Subscription mTvShowSubscription;
    private RestClient mRestClient;

    private AsynchronousContract.View mView;

    public AsynchronousPresenter( RestClient restClient, AsynchronousContract.View view) {
        this.mRestClient = restClient;
        this.mView = view;

        mView.setPresenter(this);
    }

    /**
     * If we use it with Observable.just(),
     * mRestClient.getFavoriteTvShows() will be evaluated immediately and block the UI thread.
     * Enter the Observable.fromCallable() method. It gives us two important things:
     * The code for creating the emitted value is not run until someone subscribes to the Observer.
     * The creation code can be run on a different thread.
     */
    @Override
    public void createObservable() {
        Observable<List<String>> tvShowObservable = Observable.fromCallable(() -> mRestClient.getFavoriteTvShows());
        mTvShowSubscription = tvShowObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        (List<String> tvShows) -> mView.displayTvShows(tvShows),
                        (error) -> {},
                        () -> {});
    }

    @Override
    public void subscribe() {
        createObservable();
    }

    @Override
    public void unsubscribe() {
        mTvShowSubscription.unsubscribe();
    }
}
