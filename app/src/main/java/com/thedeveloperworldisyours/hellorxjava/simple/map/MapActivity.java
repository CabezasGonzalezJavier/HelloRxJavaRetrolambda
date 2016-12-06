package com.thedeveloperworldisyours.hellorxjava.simple.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;

public class MapActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_act);

        MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.map_act_container);

        if (mapFragment == null) {
            mapFragment = new MapFragment().newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), mapFragment, R.id.map_act_container);

        new MapPresenter(mapFragment);

    }

}
