package com.thedeveloperworldisyours.hellorxjava;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        return new SimpleStringAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(SimpleStringAdapter.ViewHolder holder, final int position) {
        holder.mColorDisplay.setText(mStrings.get(position));
        holder.itemView.setOnClickListener((View v) -> {

            Intent intent = new Intent(mContext, BasicActivity.class);
            switch (position) {
                case 0:
                    intent = new Intent(mContext, BasicActivity.class);
                    break;
                case 1:
                    intent = new Intent(mContext, AsynchronousActivity.class);
                    break;
                case 2:
                    intent = new Intent(mContext, SinglesActivity.class);
                    break;
                case 3:
                    intent = new Intent(mContext, SubjectsActivity.class);
                    break;
                case 4:
                    intent = new Intent(mContext, MapActivity.class);
                    break;
                case 5:
                    intent = new Intent(mContext, TogetherActivity.class);
                    break;
            }
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mStrings.size();
    }


}
