package com.thedeveloperworldisyours.hellorxjava.complex.flatmap;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by javierg on 13/12/2016.
 */

public class DataManager {
    private final StringGenerator stringGenerator;
    private final NumberGenerator numberGenerator;
    private final Executor jobExecutor;

    public DataManager(StringGenerator stringGenerator,
                       NumberGenerator numberGenerator, Executor jobExecutor) {
        this.stringGenerator = stringGenerator;
        this.numberGenerator = numberGenerator;
        this.jobExecutor = jobExecutor;
    }

    public Observable<Integer> numbers() {
        return Observable.fromIterable(numberGenerator.numbers());
    }

    public Observable<Long> numbers(int upUntil) {
        return Observable.fromIterable(numberGenerator.numbers(upUntil));
    }

    public Flowable<Long> milliseconds(int upUntil) {
        return Flowable.interval(0, 1, TimeUnit.MILLISECONDS).take(upUntil);
    }

    public Observable<Integer> squareOfAsync(int number) {
        return Observable.just(number * number).subscribeOn(Schedulers.from(jobExecutor));
    }

    public Observable<String> elements() {
        return Observable.fromIterable(stringGenerator.randomStringList());
    }

    public Observable<String> newElement() {
        return Observable
                .just(stringGenerator.nextString())
                .map((string -> "RandomItem" + string));
    }

    public List<Integer> getNumbersSynchronously() {
        return numberGenerator.numbers();
    }
}
