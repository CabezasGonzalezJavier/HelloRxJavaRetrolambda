package com.thedeveloperworldisyours.hellorxjava.complex.flatmap;

import android.support.annotation.NonNull;
import android.util.Log;

import com.thedeveloperworldisyours.hellorxjava.Utils.scheduler.BaseSchedulerProvider;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by javierg on 13/12/2016.
 */

public class FlatMapPresenter implements FlatMapContract.Presenter {

    private static final String LOG_TAG = "ConcatVsFlatMap";
    private static final String SEPARATOR = " ";

    private DataManager mDataManager;
    private CompositeDisposable mDisposables;
    @NonNull
    private FlatMapContract.View mView;
    @NonNull
    private final BaseSchedulerProvider mSchedulerProvider;

    private Observable<Integer> buildNumbersObservable() {
        return mDataManager.numbers();
    }

    public FlatMapPresenter(@NonNull FlatMapContract.View mView, @NonNull BaseSchedulerProvider schedulerProvider) {
        this.mView = mView;
        this.mSchedulerProvider = schedulerProvider;

        mView.setPresenter(this);
    }


    @Override
    public void flatMap() {
        final DisposableObserver<Integer> observer = new DisposableObserver<Integer>() {
            final StringBuilder stringBuilder = new StringBuilder(40);

            @Override
            public void onNext(Integer number) {
                stringBuilder.append(number);
                stringBuilder.append(SEPARATOR);
                debugLog("onFlatMapClick() ------>>>> " + number);
            }

            @Override
            public void onComplete() {
                stringBuilder.append("Complete!");
                mView.printFlatMapResult(stringBuilder.toString());
                mView.showToast("flatMap() Sequence Completed!!!");
            }

            @Override
            public void onError(Throwable e) {
                // handle the exception
            }
        };

        final Observable<Integer> observable = buildNumbersObservable()
                .flatMap(mDataManager::squareOfAsync)
                .observeOn(mSchedulerProvider.ui());
        addDisposable(observable.subscribeWith(observer));
    }

    @Override
    public void concat() {
        final DisposableObserver<Integer> observer = new DisposableObserver<Integer>() {
            final StringBuilder stringBuilder = new StringBuilder(40);

            @Override
            public void onNext(Integer number) {
                stringBuilder.append(number);
                stringBuilder.append(SEPARATOR);
                debugLog("onConcatMapClick() ------>>>> " + number);
            }

            @Override
            public void onComplete() {
                stringBuilder.append("Complete!");
                mView.printConcatMapResult(stringBuilder.toString());
                mView.showToast("concatMap() Sequence Completed!!!");
            }

            @Override
            public void onError(Throwable e) {
                // handle the exception
            }
        };

        final Observable<Integer> observable = buildNumbersObservable()
                .concatMap(mDataManager::squareOfAsync)
                .observeOn(mSchedulerProvider.ui());
        addDisposable(observable.subscribeWith(observer));
    }

    private void initialize() {
        mDataManager = new DataManager(new StringGenerator(), new NumberGenerator(), JobExecutor.getInstance());
        mDisposables = new CompositeDisposable();
    }

    @Override
    public String populateData() {
        StringBuilder stringBuilder = new StringBuilder(15);
        for (int number : mDataManager.getNumbersSynchronously()) {
            stringBuilder.append(number);
            stringBuilder.append(SEPARATOR);
        }

        return stringBuilder.toString();
    }

    private void debugLog(String message) {
        Log.d(LOG_TAG, message);
    }

    private void addDisposable(Disposable disposable) {
        mDisposables.add(disposable);
    }

    @Override
    public void subscribe() {
        initialize();
    }

    @Override
    public void unsubscribe() {
        mDisposables.dispose();
    }
}
