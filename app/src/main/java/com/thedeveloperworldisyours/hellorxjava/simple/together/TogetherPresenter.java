package com.thedeveloperworldisyours.hellorxjava.simple.together;

import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by javierg on 07/12/2016.
 */

public class TogetherPresenter implements TogetherContract.Presenter {

    private PublishSubject<String> mSearchResultsSubject;
    private Subscription mTextWatchSubscription;
    private TogetherContract.View mView;
    private RestClient mRestClient;

    public TogetherPresenter(TogetherContract.View view, RestClient restClient) {

        this.mView = view;
        this.mRestClient = restClient;
        mView.setPresenter(this);
    }

    @Override
    public void createObservables() {
        mSearchResultsSubject = PublishSubject.create();
        mTextWatchSubscription = mSearchResultsSubject
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map((String string) -> mRestClient.searchForCity(string))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<String> cities) -> handleSearchResults(cities),
                        (Throwable e) -> {
                        },
                        () -> {
                        });
    }

    @Override
    public void handleSearchResults(List<String> cities) {
        if (cities.isEmpty()) {
            mView.showNoSearchResults();
        } else {
            mView.showSearchResults(cities);
        }
    }

    @Override
    public void next(CharSequence s) {
        mSearchResultsSubject.onNext(s.toString());
    }

    @Override
    public void subscribe() {
        createObservables();
    }

    @Override
    public void unsubscribe() {
        mTextWatchSubscription.unsubscribe();
    }
}
