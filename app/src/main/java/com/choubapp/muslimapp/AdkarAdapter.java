package com.choubapp.muslimapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AdkarAdapter extends RecyclerView.Adapter<AdkarAdapter.AdkarViewHolder> {
    private ArrayList<Adkar> mAdkarArrayList;
    public static class AdkarViewHolder extends RecyclerView.ViewHolder {
        public TextView mDouaa;
        public TextView mTimes;

        public AdkarViewHolder(View itemView) {
            super(itemView);
            mDouaa = itemView.findViewById(R.id.douaa);
            mTimes = itemView.findViewById(R.id.times);
        }
    }
    public AdkarAdapter(ArrayList<Adkar> adkarList) {
        mAdkarArrayList = adkarList;
    }
    @NonNull
    @Override
    public AdkarViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adkar, viewGroup, false);
        AdkarViewHolder evh = new AdkarViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AdkarViewHolder adkarViewHolder, int i) {
        Adkar currentItem = mAdkarArrayList.get(i);
        adkarViewHolder.mDouaa.setText(currentItem.getDouaa());
        adkarViewHolder.mTimes.setText(currentItem.getTimes());
    }

    @Override
    public int getItemCount() {
        return mAdkarArrayList.size();
    }
}
