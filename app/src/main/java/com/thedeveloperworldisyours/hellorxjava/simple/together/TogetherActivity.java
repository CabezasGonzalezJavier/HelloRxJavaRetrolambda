package com.thedeveloperworldisyours.hellorxjava.simple.together;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;
import com.thedeveloperworldisyours.hellorxjava.Utils.RestClient;

public class TogetherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.together_act);

        RestClient restClient = new RestClient(this);

        TogetherFragment togetherFragment = (TogetherFragment) getSupportFragmentManager().findFragmentById(R.id.together_act_container);
        if (togetherFragment == null){
            togetherFragment = TogetherFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), togetherFragment, R.id.together_act_container);

        new TogetherPresenter(togetherFragment, restClient);
    }
}
