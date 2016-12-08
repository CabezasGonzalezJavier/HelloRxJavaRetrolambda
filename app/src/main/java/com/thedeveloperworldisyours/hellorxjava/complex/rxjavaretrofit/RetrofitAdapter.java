package com.thedeveloperworldisyours.hellorxjava.complex.rxjavaretrofit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thedeveloperworldisyours.hellorxjava.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by javierg on 08/12/2016.
 */

public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.ViewHolder> {

    private List<GithubUserList> mList = new ArrayList<>();

    @Override
    public RetrofitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.retrofit_list_item, parent, false);

        return new RetrofitAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mLog.setText(mList.get(position).getLogin());
        holder.mBlog.setText(mList.get(position).getBlog());
        holder.mInt.setText(String.valueOf(mList.get(position).getPublicRepos()));
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setElements(List<GithubUserList> newGiu) {
        mList.clear();
        mList.addAll(newGiu);
        notifyDataSetChanged();
    }

    public void setNewElement(GithubUserList user) {
        mList.add(mList.size(), user);
        notifyDataSetChanged();
    }

    public void setClear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView mLog;
        public final TextView mBlog;
        public final TextView mInt;

        public ViewHolder(View view) {
            super(view);
            mLog = (TextView) view.findViewById(R.id.retrofit_list_item_log);
            mBlog = (TextView) view.findViewById(R.id.retrofit_list_item_blog);
            mInt = (TextView) view.findViewById(R.id.retrofit_list_item_int);
        }
    }
}

