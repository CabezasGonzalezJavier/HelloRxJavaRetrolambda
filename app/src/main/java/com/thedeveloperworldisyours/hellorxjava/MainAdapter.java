package com.thedeveloperworldisyours.hellorxjava;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierg on 02/12/2016.
 */

public class MainAdapter extends RecyclerView.Adapter<SimpleStringAdapter.ViewHolder> {

    private final Context mContext;
    private List<String> mStrings = new ArrayList<>();

    public MainAdapter(Context context, List<String> list) {
        mContext = context;
        mStrings = list;
    }

    @Override
    public SimpleStringAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.string_list_item, parent, false);

        SimpleStringAdapter.ViewHolder dataObjectHolder = new SimpleStringAdapter.ViewHolder(view);

        return dataObjectHolder;

    }

    @Override
    public void onBindViewHolder(SimpleStringAdapter.ViewHolder holder, final int position) {
        holder.mColorDisplay.setText(mStrings.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(mContext, BasicActivity.class);
                        mContext.startActivity(intent);
                        break;
                    case 1:
                        Intent intentAsynchronousActivity = new Intent(mContext, AsynchronousActivity.class);
                        mContext.startActivity(intentAsynchronousActivity);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }


}
