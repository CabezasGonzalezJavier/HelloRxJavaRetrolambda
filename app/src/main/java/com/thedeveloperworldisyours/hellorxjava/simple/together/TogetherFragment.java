package com.thedeveloperworldisyours.hellorxjava.simple.together;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.SimpleStringAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TogetherFragment extends Fragment implements TogetherContract.View{

    @BindView(R.id.together_frag_search_input)
    EditText mSearchInput;

    @BindView(R.id.together_frag_no_results_indicator)
    TextView mNoResultsIndicator;

    @BindView(R.id.together_frag_search_results)
    RecyclerView mSearchResults;

    private SimpleStringAdapter mSearchResultsAdapter;
    private TogetherContract.Presenter mPresenter;

    public TogetherFragment() {
        // Required empty public constructor
    }

    public static TogetherFragment newInstance() {
        return new TogetherFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.together_frag, container, false);

        ButterKnife.bind(this, view);

        mSearchResults.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSearchResultsAdapter = new SimpleStringAdapter(getActivity());
        mSearchResults.setAdapter(mSearchResultsAdapter);
        listenToSearchInput();

        return view;
    }

    @Override
    public void showNoSearchResults() {
        mNoResultsIndicator.setVisibility(View.VISIBLE);
        mSearchResults.setVisibility(View.GONE);
    }

    @Override
    public void showSearchResults(List<String> cities) {
        mNoResultsIndicator.setVisibility(View.GONE);
        mSearchResults.setVisibility(View.VISIBLE);
        mSearchResultsAdapter.setStrings(cities);
    }

    public void listenToSearchInput() {
        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPresenter.next(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void setPresenter(TogetherContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }
}
