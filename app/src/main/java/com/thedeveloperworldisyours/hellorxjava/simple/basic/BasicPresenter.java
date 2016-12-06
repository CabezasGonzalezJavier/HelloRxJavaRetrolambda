package com.thedeveloperworldisyours.hellorxjava.simple.basic;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by javierg on 06/12/2016.
 */

public class BasicPresenter implements BasicContract.Presenter {


    BasicContract.View mView;

    public BasicPresenter(BasicContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    @Override
    public List<String> getColorList() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("blue");
        colors.add("green");
        colors.add("red");
        colors.add("chartreuse");
        colors.add("Van Dyke Brown");
        return colors;
    }

    /**
     * This method creates an Observable such that when an Observer subscribes,
     * the onNext() of the Observer is immediately called with the argument provided
     * to Observable.just(). The onCompleted() will then be called since
     * the Observable has no other values to emit.
     */
    @Override
    public void createObservable() {
        Observable<List<String>> listObservable = Observable.just(getColorList());

        listObservable.subscribe(
                (List<String> colors)-> mView.setInfo(colors),
                (error) -> {},
                () -> {});
    }

    @Override
    public void subscribe() {
        createObservable();
    }

    @Override
    public void unsubscribe() {
//        listObservable.unsubscribeOn(this);?
    }
}
