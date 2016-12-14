package com.thedeveloperworldisyours.hellorxjava.simple.asynchronous;

import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;
import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;

import java.util.List;

import io.reactivex.Observable;


/**
 * Created by javierg on 06/12/2016.
 */

public class AsynchronousPresenter implements AsynchronousContract.Presenter {

    private RestClient mRestClient;

    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    private AsynchronousContract.View mView;

    public AsynchronousPresenter(RestClient restClient, @NonNull BaseSchedulerProvider schedulerProvider, AsynchronousContract.View view) {
        this.mRestClient = restClient;
        this.mSchedulerProvider = schedulerProvider;
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

        getList()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe(
                        (List<String> tvShows) -> mView.displayTvShows(tvShows),
                        (error) -> mView.errorTvShows(),
                        () -> {});
    }

    public Observable<List<String>> getList(){
        return Observable.fromCallable(() -> mRestClient.getFavoriteTvShows());
    }

    @Override
    public void subscribe() {
        createObservable();
    }

    @Override
    public void unsubscribe() {
//        mTvShowSubscription.unsubscribe();
    }
}
