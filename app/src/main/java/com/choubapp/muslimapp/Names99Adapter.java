package com.choubapp.muslimapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Names99Adapter extends RecyclerView.Adapter<Names99Adapter.Names99ViewHolder> {
    private ArrayList<Names99> mExampleList;
    public static class Names99ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public Names99ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.names99);
        }
    }

    public Names99Adapter(ArrayList<Names99> exampleList) {
        mExampleList = exampleList;
    }
    @Override
    public Names99ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.names99, parent, false);
        Names99ViewHolder evh = new Names99ViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(Names99ViewHolder holder, int position) {
        Names99 currentItem = mExampleList.get(position);
        holder.mTextView.setText(currentItem.getText());
    }
    @Override
    public int getItemCount() {
        return mExampleList.size();
    }

}
