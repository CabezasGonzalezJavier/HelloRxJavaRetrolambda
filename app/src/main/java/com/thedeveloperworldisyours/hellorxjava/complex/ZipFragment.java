package com.thedeveloperworldisyours.hellorxjava.complex;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thedeveloperworldisyours.hellorxjava.R;

public class ZipFragment extends Fragment implements ZipContract.View{

    private View mView;

    private ZipContract.Presenter mPresenter;

    public ZipFragment() {
        // Required empty public constructor
    }

    public static ZipFragment newInstance() {
        return new ZipFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         mView = inflater.inflate(R.layout.zip_frag, container, false);
        return mView;
    }

    @Override
    public void show(UserAndEvents o) {
        Snackbar.make(mView, o.toString(), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(ZipContract.Presenter presenter) {
        mPresenter= presenter;
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
