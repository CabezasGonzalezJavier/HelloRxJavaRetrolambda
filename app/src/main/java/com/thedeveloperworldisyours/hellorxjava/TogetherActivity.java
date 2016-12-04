package com.thedeveloperworldisyours.hellorxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class TogetherActivity extends AppCompatActivity {

    @BindView(R.id.search_input)
    EditText mSearchInput;

    @BindView(R.id.no_results_indicator)
    TextView mNoResultsIndicator;

    @BindView(R.id.search_results)
    RecyclerView mSearchResults;

    private RestClient mRestClient;
    private SimpleStringAdapter mSearchResultsAdapter;

    private PublishSubject<String> mSearchResultsSubject;
    private Subscription mTextWatchSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRestClient = new RestClient(this);
        configureLayout();
        createObservables();
        listenToSearchInput();
    }

    private void createObservables() {
        mSearchResultsSubject = PublishSubject.create();
        mTextWatchSubscription = mSearchResultsSubject
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map( (String string) -> mRestClient.searchForCity(string))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<String> cities) {
                        handleSearchResults(cities);
                    }
                });
    }

    private void handleSearchResults(List<String> cities) {
        if (cities.isEmpty()) {
            showNoSearchResults();
        } else {
            showSearchResults(cities);
        }
    }

    private void showNoSearchResults() {
        mNoResultsIndicator.setVisibility(View.VISIBLE);
        mSearchResults.setVisibility(View.GONE);
    }

    private void showSearchResults(List<String> cities) {
        mNoResultsIndicator.setVisibility(View.GONE);
        mSearchResults.setVisibility(View.VISIBLE);
        mSearchResultsAdapter.setStrings(cities);
    }

    private void listenToSearchInput() {
        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mSearchResultsSubject.onNext(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void configureLayout() {
        setContentView(R.layout.together_act);
        mSearchResults.setLayoutManager(new LinearLayoutManager(this));
        mSearchResultsAdapter = new SimpleStringAdapter(this);
        mSearchResults.setAdapter(mSearchResultsAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTextWatchSubscription != null && !mTextWatchSubscription.isUnsubscribed()) {
            mTextWatchSubscription.unsubscribe();
        }
    }
}
