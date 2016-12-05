package com.thedeveloperworldisyours.hellorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.Observer;

public class BasicActivity extends AppCompatActivity {

    @BindView(R.id.main_act_color_list)
    RecyclerView mColorListView;

    SimpleStringAdapter mSimpleStringAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        configureLayout();
        createObservable();
    }

    /**
     * This method creates an Observable such that when an Observer subscribes,
     * the onNext() of the Observer is immediately called with the argument provided
     * to Observable.just(). The onCompleted() will then be called since
     * the Observable has no other values to emit.
     */
    private void createObservable() {
        Observable<List<String>> listObservable = Observable.just(getColorList());

        listObservable.subscribe(
                (List<String> colors)-> mSimpleStringAdapter.setStrings(colors),
                (error) -> {},
                () -> {});
    }

    private void configureLayout() {
        setContentView(R.layout.basic_act);
        mColorListView = (RecyclerView) findViewById(R.id.main_act_color_list);
        mColorListView.setLayoutManager(new LinearLayoutManager(this));
        mSimpleStringAdapter = new SimpleStringAdapter(this);
        mColorListView.setAdapter(mSimpleStringAdapter);
    }

    private static List<String> getColorList() {
        ArrayList<String> colors = new ArrayList<>();
        colors.add("blue");
        colors.add("green");
        colors.add("red");
        colors.add("chartreuse");
        colors.add("Van Dyke Brown");
        return colors;
    }

}
