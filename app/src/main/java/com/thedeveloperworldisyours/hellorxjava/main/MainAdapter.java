package com.thedeveloperworldisyours.hellorxjava.main;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thedeveloperworldisyours.hellorxjava.R;
import com.thedeveloperworldisyours.hellorxjava.Utils.SimpleStringAdapter;
import com.thedeveloperworldisyours.hellorxjava.complex.ZipActivity;
import com.thedeveloperworldisyours.hellorxjava.complex.ZipFragment;
import com.thedeveloperworldisyours.hellorxjava.simple.asynchronous.AsynchronousActivity;
import com.thedeveloperworldisyours.hellorxjava.simple.basic.BasicActivity;
import com.thedeveloperworldisyours.hellorxjava.simple.map.MapActivity;
import com.thedeveloperworldisyours.hellorxjava.simple.singles.SinglesActivity;
import com.thedeveloperworldisyours.hellorxjava.simple.subjects.SubjectsActivity;
import com.thedeveloperworldisyours.hellorxjava.simple.together.TogetherActivity;

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
                case 6:
                    intent = new Intent(mContext, ZipActivity.class);
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
