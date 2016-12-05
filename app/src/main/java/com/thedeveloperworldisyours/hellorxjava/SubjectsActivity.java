package com.thedeveloperworldisyours.hellorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.subjects.PublishSubject;

public class SubjectsActivity extends AppCompatActivity {

    @BindView(R.id.subjects_act_counter_display)
    public TextView mCounterDisplay;

    private PublishSubject<Integer> mCounterEmitter;

    private int mCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureLayout();
        createCounterEmitter();
    }

    /**
     * With a PublishSubject, as soon as you put something in one end
     * of the pipe it immediately comes out the other.
     */
    private void createCounterEmitter() {
        mCounterEmitter = PublishSubject.create();
        mCounterEmitter.subscribe(
                (Integer integer) ->  mCounterDisplay.setText(String.valueOf(integer)),
                (Throwable e) ->{},
                () -> { });
    }

    private void configureLayout() {
        setContentView(R.layout.subjects_act);
        configureCounterDisplay();
    }

    private void configureCounterDisplay() {
        ButterKnife.bind(this);

        mCounterDisplay.setText(String.valueOf(mCounter));
    }

    @OnClick(R.id.subjects_act_increment_button)
    public void configureIncrementButton() {
                onIncrementButtonClick();
    }

    /**
     * It increments a variable called mCounter.
     * It calls onNext() on the mCounterEmitter with the new value of mCounter.
     */
    private void onIncrementButtonClick() {
        mCounter++;
        mCounterEmitter.onNext(mCounter);
    }
}
