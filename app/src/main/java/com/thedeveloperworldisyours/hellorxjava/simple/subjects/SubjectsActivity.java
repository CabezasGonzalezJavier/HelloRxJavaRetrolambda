package com.thedeveloperworldisyours.hellorxjava.simple.subjects;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;

public class SubjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subjects_act);

        SubjectsFragment subjectsFragment = (SubjectsFragment) getSupportFragmentManager().findFragmentById(R.id.subjects_act_container);

        if (subjectsFragment == null) {
            subjectsFragment = SubjectsFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), subjectsFragment, R.id.subjects_act_container);

        new SubjectsPresenter(subjectsFragment);

    }
}
