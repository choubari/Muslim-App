package com.choubapp.muslimapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class FadlAdapter extends RecyclerView.Adapter<FadlAdapter.FadlViewHolder> {
    private ArrayList<Fadl> mFadlArrayList;
    public static class FadlViewHolder extends RecyclerView.ViewHolder {
        public TextView mThought;
        public TextView mSaidby;

        public FadlViewHolder(View itemView) {
            super(itemView);
            mThought = itemView.findViewById(R.id.thought);
            mSaidby = itemView.findViewById(R.id.saidby);
        }
    }
    public FadlAdapter(ArrayList<Fadl> fadlList) {
        mFadlArrayList = fadlList;
    }
    @NonNull
    @Override
    public FadlViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fadl, viewGroup, false);
        FadlViewHolder evh = new FadlViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull FadlViewHolder fadlViewHolder, int i) {
        Fadl currentItem = mFadlArrayList.get(i);
        fadlViewHolder.mThought.setText(currentItem.getThought());
        fadlViewHolder.mSaidby.setText(currentItem.getSaidby());
    }

    @Override
    public int getItemCount() {
        return mFadlArrayList.size();
    }
}
