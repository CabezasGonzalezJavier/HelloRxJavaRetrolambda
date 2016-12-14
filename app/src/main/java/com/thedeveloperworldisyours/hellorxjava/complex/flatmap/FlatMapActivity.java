package com.thedeveloperworldisyours.hellorxjava.complex.flatmap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.hellorxjava.Injection;
import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;

public class FlatMapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flat_map_act);

        FlatMapFragment flatMapFragment = (FlatMapFragment) getSupportFragmentManager().findFragmentById(R.id.flat_map_act_container);
        if (flatMapFragment == null){
            flatMapFragment = new FlatMapFragment().newInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), flatMapFragment, R.id.flat_map_act_container);

        new FlatMapPresenter(flatMapFragment, Injection.provideSchedulerProvider());

    }

}
