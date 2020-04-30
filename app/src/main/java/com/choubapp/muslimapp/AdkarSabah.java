package com.choubapp.muslimapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class AdkarSabah extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adkar_sabah);
        ArrayList<Adkar> AdkarSabahList = new ArrayList<>();
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah1), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah2), getString(R.string.time4)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah3), getString(R.string.time7)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah4), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah5), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah6), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah7), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah8), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah9), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah10), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah11), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah12), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah13), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah14), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah15), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah16), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah17), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah18), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah19), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah20), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah21), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah22), getString(R.string.time10)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah23), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah24), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah25), getString(R.string.time100)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah26), getString(R.string.time100)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah27), getString(R.string.time100)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah28), getString(R.string.time1)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah29), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah30), getString(R.string.time3)));
        AdkarSabahList.add(new Adkar(getString(R.string.adkarsabah31), getString(R.string.time3)));




        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdkarAdapter(AdkarSabahList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
