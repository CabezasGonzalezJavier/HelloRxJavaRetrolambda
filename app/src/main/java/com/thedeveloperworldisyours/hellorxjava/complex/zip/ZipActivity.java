package com.thedeveloperworldisyours.hellorxjava.complex.zip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thedeveloperworldisyours.hellorxjava.Injection;
import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;

public class ZipActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zip_act);

        ZipFragment zipFragment = (ZipFragment) getSupportFragmentManager().findFragmentById(R.id.zip_act_container);

        if (zipFragment == null) {
            zipFragment= ZipFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), zipFragment, R.id.zip_act_container);

        new ZipPresenter(zipFragment, Injection.provideSchedulerProvider());

    }

}
