package com.thedeveloperworldisyours.hellorxjava.simple.map;

import com.thedeveloperworldisyours.hellorxjava.BasePresenter;
import com.thedeveloperworldisyours.hellorxjava.BaseView;

/**
 * Created by javierg on 06/12/2016.
 */

public class MapContract {

    public interface View extends BaseView<Presenter>{
        void setInfo(String value);

    }

    public interface Presenter extends BasePresenter{
        void loop();
    }
}
