package com.thedeveloperworldisyours.hellorxjava.simple.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thedeveloperworldisyours.hellorxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapFragment extends Fragment implements MapContract.View{

    @BindView(R.id.map_act_value_display)
    TextView mValueDisplay;

    MapContract.Presenter mPresenter;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
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
        View view = inflater.inflate(R.layout.map_frag, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setInfo(String value) {
        mValueDisplay.setText(value);
    }

    @Override
    public void setPresenter(MapContract.Presenter presenter) {
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
