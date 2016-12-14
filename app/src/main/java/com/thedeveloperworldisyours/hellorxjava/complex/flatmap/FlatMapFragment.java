package com.thedeveloperworldisyours.hellorxjava.complex.flatmap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thedeveloperworldisyours.hellorxjava.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FlatMapFragment extends Fragment implements FlatMapContract.View{

    private static final String LOG_TAG = "ConcatVsFlatMap";
    private static final String SEPARATOR = " ";

    View mView;

    @BindView(R.id.flat_map_frag_tv_streamOriginalOrder)
    TextView tv_streamOriginalOrder;
    @BindView(R.id.flat_map_frag_tv_flatMapResult)
    TextView tv_flatMapResult;
    @BindView(R.id.flat_map_frag_tv_concatMapResult)
    TextView tv_concatMapResult;

    FlatMapContract.Presenter mPresenter;

    public FlatMapFragment() {
        // Required empty public constructor
    }

    public static FlatMapFragment newInstance() {
        return new FlatMapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.flat_map_frag, container, false);

        ButterKnife.bind(this, mView);

        return mView;
    }

    @OnClick(R.id.flat_map_frag_btn_concatMap)
    void onConcatMapClick() {
        mPresenter.concat();
    }

    @OnClick(R.id.flat_map_frag_btn_flatMap)
    void onFlatMapClick() {
        mPresenter.flatMap();
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void debugLog(String message) {
        Log.d(LOG_TAG, message);
    }

    @Override
    public void printFlatMapResult(String result) {
        tv_flatMapResult.setText(result);
    }

    @Override
    public void printConcatMapResult(String result) {
        tv_concatMapResult.setText(result);
    }

    @Override
    public void populateData() {
        this.tv_streamOriginalOrder.setText(mPresenter.populateData());
    }

    @Override
    public void setPresenter(FlatMapContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
        populateData();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }
}
