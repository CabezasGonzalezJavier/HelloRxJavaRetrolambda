package com.thedeveloperworldisyours.hellorxjava.simple.subjects;


import io.reactivex.subjects.PublishSubject;

/**
 * Created by javierg on 06/12/2016.
 */

public class SubjectsPresenter implements SubjectsContract.Presenter {

    private SubjectsContract.View mView;

    private PublishSubject<Integer> mCounterEmitter;

    private int mCounter = 0;

    public SubjectsPresenter(SubjectsContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * With a PublishSubject, as soon as you put something in one end
     * of the pipe it immediately comes out the other.
     */
    @Override
    public void createCounterEmitter() {
        mCounterEmitter = PublishSubject.create();
        mCounterEmitter.subscribe(
                (Integer integer) ->  mView.setInfo(String.valueOf(integer)),
                (Throwable e) ->{},
                () -> { });
    }

    /**
     * It increments a variable called mCounter.
     * It calls onNext() on the mCounterEmitter with the new value of mCounter.
     */
    @Override
    public void onIncrementButtonClick() {
        mCounter++;
        mCounterEmitter.onNext(mCounter);
    }

    @Override
    public void subscribe() {
        createCounterEmitter();
    }

    @Override
    public void unsubscribe() {

    }
}
