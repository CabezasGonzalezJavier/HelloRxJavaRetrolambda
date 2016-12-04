package com.thedeveloperworldisyours.hellorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Single;
import rx.SingleSubscriber;
import rx.functions.Func1;

public class MapActivity extends AppCompatActivity {

    @BindView(R.id.map_act_value_display)
    TextView mValueDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureLayout();

        /**
         * You can think of map as a function that takes in one value and outputs another value.
         * Usually there is some relationship between value put in to the map and the value that is output.
         */
        Single.just(4).map((Integer integer) -> String.valueOf(integer))

                .subscribe(new SingleSubscriber<String>() {
                    @Override
                    public void onSuccess(String value) {
                        mValueDisplay.setText(value);
                    }

                    @Override
                    public void onError(Throwable error) {

                    }
                });
    }

    private void configureLayout() {
        setContentView(R.layout.map_act);

        ButterKnife.bind(this);

    }
}
