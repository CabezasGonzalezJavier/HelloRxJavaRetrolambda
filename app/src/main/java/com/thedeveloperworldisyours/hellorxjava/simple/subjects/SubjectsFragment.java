package com.thedeveloperworldisyours.hellorxjava.simple.subjects;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thedeveloperworldisyours.hellorxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SubjectsFragment extends Fragment implements SubjectsContract.View{
    @BindView(R.id.subjects_frag_counter_display)
    public TextView mCounterDisplay;

    private SubjectsContract.Presenter mPresenter;
    public SubjectsFragment() {
        // Required empty public constructor
    }

    public static SubjectsFragment newInstance() {
        SubjectsFragment fragment = new SubjectsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.subjects_frag, container, false);

        ButterKnife.bind(this, view);

        mCounterDisplay.setText(String.valueOf(0));
        return view;
    }

    @OnClick(R.id.subjects_frag_increment_button)
    public void configureIncrementButton() {
        mPresenter.onIncrementButtonClick();
    }

    @Override
    public void setInfo(String info) {

        mCounterDisplay.setText(info);
    }

    @Override
    public void setPresenter(SubjectsContract.Presenter presenter) {
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
