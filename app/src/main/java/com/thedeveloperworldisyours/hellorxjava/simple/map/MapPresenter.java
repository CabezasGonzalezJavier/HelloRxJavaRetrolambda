package com.thedeveloperworldisyours.hellorxjava.simple.map;

import rx.Single;

/**
 * Created by javierg on 06/12/2016.
 */

public class MapPresenter implements MapContract.Presenter {

    public MapContract.View mView;

    public MapPresenter(MapContract.View view) {
        this.mView = view;
        mView.setPresenter(this);
    }

    /**
     * You can think of map as a function that takes in one value and outputs another value.
     * Usually there is some relationship between value put in to the map and the value that is output.
     */
    @Override
    public void loop() {
        Single.just(4).map((Integer integer) -> String.valueOf(integer))

                .subscribe((String value) -> mView.setInfo(value),
                        (Throwable error) -> {
                        });

    }

    @Override
    public void subscribe() {
        loop();
    }

    @Override
    public void unsubscribe() {

    }
}
