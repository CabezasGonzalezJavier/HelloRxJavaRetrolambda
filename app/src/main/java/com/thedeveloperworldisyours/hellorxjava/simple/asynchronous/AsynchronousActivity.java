package com.thedeveloperworldisyours.hellorxjava.simple.asynchronous;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;

public class AsynchronousActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.asynchronous_act);

        AsynchronousFragment asynchronousFragment = (AsynchronousFragment) getSupportFragmentManager().findFragmentById(R.id.asynchronous_act_content);
        if (asynchronousFragment == null) {
            asynchronousFragment = AsynchronousFragment.newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), asynchronousFragment, R.id.asynchronous_act_content);


        RestClient restClient = new RestClient(this);

        new AsynchronousPresenter(restClient, asynchronousFragment);
    }

}
