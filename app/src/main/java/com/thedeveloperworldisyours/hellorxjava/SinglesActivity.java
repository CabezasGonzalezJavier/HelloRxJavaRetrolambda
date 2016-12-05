package com.thedeveloperworldisyours.hellorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SinglesActivity extends AppCompatActivity {

    @BindView(R.id.singles_act_tv_show_list)
    public RecyclerView mTvShowListView;

    @BindView(R.id.singles_act_loader)
    public ProgressBar mProgressBar;

    @BindView(R.id.singles_act_error_message)
    public TextView mErrorMessage;

    private Subscription mTvShowSubscription;
    private SimpleStringAdapter mSimpleStringAdapter;
    private RestClient mRestClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestClient = new RestClient(this);
        configureLayout();
        createSingle();
    }

    /**
     * As it turns out, there’s a simpler version of an Observable called a Single.
     * Singles work almost exactly the same as Observables. But instead of there being
     * an onCompleted(), onNext(), and onError(), there are only two callbacks:
     * onSuccess() and onError().
     */
    private void createSingle() {

        Single<List<String>> tvShowSingle = Single.fromCallable(() -> mRestClient.getFavoriteTvShows());

        mTvShowSubscription = tvShowSingle
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((List<String> tvShows) -> displayTvShows(tvShows),
                    (Throwable error) ->  displayErrorMessage());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mTvShowSubscription != null && !mTvShowSubscription.isUnsubscribed()) {
            mTvShowSubscription.unsubscribe();
        }
    }

    private void displayTvShows(List<String> tvShows) {
        mSimpleStringAdapter.setStrings(tvShows);
        mProgressBar.setVisibility(View.GONE);
        mTvShowListView.setVisibility(View.VISIBLE);
    }

    private void displayErrorMessage() {
        mProgressBar.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    private void configureLayout() {
        setContentView(R.layout.singles_act);

        ButterKnife.bind(this);

        mTvShowListView.setLayoutManager(new LinearLayoutManager(this));
        mSimpleStringAdapter = new SimpleStringAdapter(this);
        mTvShowListView.setAdapter(mSimpleStringAdapter);
    }
}
