package com.thedeveloperworldisyours.hellorxjava.simple.basic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.SimpleStringAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasicFragment extends Fragment implements BasicContract.View{

    @BindView(R.id.basic_frag_color_list)
    RecyclerView mColorListView;

    SimpleStringAdapter mSimpleStringAdapter;
    BasicContract.Presenter mPresenter;

    public BasicFragment() {
        // Required empty public constructor
    }

    public static BasicFragment newInstance() {
        return new BasicFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.basic_frag, container, false);
        ButterKnife.bind(this, view);
        configureLayout();
        return view;
    }

    @Override
    public void configureLayout() {
        mColorListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSimpleStringAdapter = new SimpleStringAdapter(getActivity());
        mColorListView.setAdapter(mSimpleStringAdapter);
    }

    @Override
    public void setInfo(List<String> colors) {
        mSimpleStringAdapter.setStrings(colors);
    }

    @Override
    public void setPresenter(BasicContract.Presenter presenter) {
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
}
