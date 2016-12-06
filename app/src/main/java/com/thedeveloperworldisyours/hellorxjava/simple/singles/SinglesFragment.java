package com.thedeveloperworldisyours.hellorxjava.simple.singles;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.SimpleStringAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SinglesFragment extends Fragment implements SinglesContract.View{

    @BindView(R.id.singles_frag_tv_show_list)
    public RecyclerView mTvShowListView;

    @BindView(R.id.singles_frag_loader)
    public ProgressBar mProgressBar;

    @BindView(R.id.singles_frag_error_message)
    public TextView mErrorMessage;


    private SimpleStringAdapter mSimpleStringAdapter;
    private SinglesContract.Presenter mPresenter;

    public SinglesFragment() {
        // Required empty public constructor
    }

    public static SinglesFragment newInstance() {
        return new SinglesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.singles_frag, container, false);

        ButterKnife.bind(this, view);

        mTvShowListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSimpleStringAdapter = new SimpleStringAdapter(getActivity());
        mTvShowListView.setAdapter(mSimpleStringAdapter);

        return view;
    }

    @Override
    public void displayTvShows(List<String> tvShowsList) {
        mSimpleStringAdapter.setStrings(tvShowsList);
        mProgressBar.setVisibility(View.GONE);
        mTvShowListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayErrorMessage() {
        mProgressBar.setVisibility(View.GONE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void setPresenter(SinglesContract.Presenter presenter) {
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
