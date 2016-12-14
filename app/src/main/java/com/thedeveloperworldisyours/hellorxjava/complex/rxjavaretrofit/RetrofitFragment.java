package com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.percent.PercentRelativeLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.thedeveloperworldisyours.hellorxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RetrofitFragment extends Fragment implements RetrofitContract.View {

    RetrofitContract.Presenter mPresenter;

    private RetrofitAdapter mRetrofitAdapter;

    @BindView(R.id.retrofit_frag_recycle_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.retrofit_frag_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.retrofit_frag_no_data)
    TextView mNoData;

    @BindView(R.id.retrofit_frag_container)
    PercentRelativeLayout mContainer;

    View mView;

    public RetrofitFragment() {
        // Required empty public constructor
    }

    public static RetrofitFragment newInstance() {
        return new RetrofitFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.retrofit_frag, container, false);
        ButterKnife.bind(this, mView);
        configureLayout();
        return mView;
    }

    @Override
    public void configureLayout() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRetrofitAdapter = new RetrofitAdapter();
        mRecyclerView.setAdapter(mRetrofitAdapter);
    }

    @Override
    public void showInfo(GithubUserList user) {
        mRetrofitAdapter.setNewElement(user);
        mProgressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String error) {
        mNoData.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        Snackbar.make(mView, error, Snackbar.LENGTH_LONG);
    }

    @Override
    public void setPresenter(RetrofitContract.Presenter presenter) {
        mPresenter = presenter;
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

    @OnClick(R.id.retrofit_frag_fetch)
    public void fetch(View view) {
        mNoData.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mPresenter.fetch();
    }

    @OnClick(R.id.retrofit_frag_clear)
    public void clear(View view) {
        mNoData.setVisibility(View.VISIBLE);
        mRetrofitAdapter.setClear();
    }
}
