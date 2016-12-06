package com.thedeveloperworldisyours.hellorxjava.simple.singles;

import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;

import java.util.List;

import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by javierg on 06/12/2016.
 */

public class SinglesPresenter implements SinglesContract.Presenter {
    private Subscription mTvShowSubscription;
    private RestClient mRestClient;
    public SinglesContract.View mView;

    public SinglesPresenter(SinglesContract.View view, RestClient restClient) {
        this.mView = view;
        this.mRestClient = restClient;
        mView.setPresenter(this);
    }

    /**
     * As it turns out, there’s a simpler version of an Observable called a Single.
     * Singles work almost exactly the same as Observables. But instead of there being
     * an onCompleted(), onNext(), and onError(), there are only two callbacks:
     * onSuccess() and onError().
     */
    @Override
    public void createSingle() {
        Single<List<String>> tvShowSingle = Single.fromCallable(() -> mRestClient.getFavoriteTvShows());

        mTvShowSubscription = tvShowSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<String> tvShows) -> mView.displayTvShows(tvShows),
                        (Throwable error) ->  mView.displayErrorMessage());
    }

    @Override
    public void subscribe() {
        createSingle();
    }

    @Override
    public void unsubscribe() {
        mTvShowSubscription.unsubscribe();
    }
}
