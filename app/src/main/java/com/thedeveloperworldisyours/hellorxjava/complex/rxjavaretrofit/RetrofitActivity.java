package com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.hellorxjava.Injection;
import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.ActivityUtils;

public class RetrofitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.retrofit_act);

        RetrofitFragment retrofitFragment = (RetrofitFragment) getSupportFragmentManager().findFragmentById(R.id.retrofit_act_container);
        if (retrofitFragment == null) {
            retrofitFragment = RetrofitFragment.newInstance();
        }

        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), retrofitFragment, R.id.retrofit_act_container);

        new RetrofitPresenter(retrofitFragment, Injection.provideSchedulerProvider());

    }
}
