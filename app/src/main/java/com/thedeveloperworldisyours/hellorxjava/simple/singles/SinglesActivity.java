package com.thedeveloperworldisyours.hellorxjava.simple.singles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;
import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;

public class SinglesActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singles_act);

        SinglesFragment singlesFragment = (SinglesFragment) getSupportFragmentManager().findFragmentById(R.id.singles_act_container);

        if (singlesFragment == null) {
            singlesFragment = new SinglesFragment().newInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), singlesFragment, R.id.singles_act_container);

        RestClient restClient = new RestClient(this);
        new SinglesPresenter(singlesFragment, restClient);
    }
}
