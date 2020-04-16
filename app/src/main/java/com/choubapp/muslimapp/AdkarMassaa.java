package com.choubapp.muslimapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AdkarMassaa extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adkar_massaa);

        ArrayList<Adkar> AdkarMassaaList = new ArrayList<>();
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah1), getString(R.string.time100)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah1), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah1), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah1), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah1), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah1), getString(R.string.time3)));


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdkarAdapter(AdkarMassaaList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
