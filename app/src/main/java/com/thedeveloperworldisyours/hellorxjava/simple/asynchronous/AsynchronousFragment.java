package com.thedeveloperworldisyours.hellorxjava.simple.asynchronous;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.SimpleStringAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AsynchronousFragment extends Fragment implements AsynchronousContract.View {

    @BindView(R.id.asynchronous_frag_tv_show_list)
    public RecyclerView mTvShowListView;

    @BindView(R.id.asynchronous_frag_loader)
    public ProgressBar mProgressBar;

    AsynchronousContract.Presenter mPresenter;
    private SimpleStringAdapter mSimpleStringAdapter;

    public AsynchronousFragment() {
        // Required empty public constructor
    }

    public static AsynchronousFragment newInstance() {
        return new AsynchronousFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.asynchronous_frag, container, false);
        ButterKnife.bind(this, view);
        configureLayout();
        return view;
    }

    private void configureLayout() {

        mTvShowListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSimpleStringAdapter = new SimpleStringAdapter(getActivity());
        mTvShowListView.setAdapter(mSimpleStringAdapter);
    }

    @Override
    public void displayTvShows(List<String> tvShows) {
        mSimpleStringAdapter.setStrings(tvShows);
        mProgressBar.setVisibility(View.GONE);
        mTvShowListView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void setPresenter(AsynchronousContract.Presenter presenter) {
        mPresenter = presenter;
    }
}
