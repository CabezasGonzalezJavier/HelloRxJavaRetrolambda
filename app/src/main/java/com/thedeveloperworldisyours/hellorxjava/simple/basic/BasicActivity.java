package com.thedeveloperworldisyours.hellorxjava.simple.basic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;

public class BasicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.basic_act);
        BasicFragment basicFragment = (BasicFragment) getSupportFragmentManager().findFragmentById(R.id.basic_act_root);

        if (basicFragment == null) {
            basicFragment = new BasicFragment().newInstance();
        }
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), basicFragment, R.id.basic_act_root);

        new BasicPresenter(basicFragment);
    }


}
