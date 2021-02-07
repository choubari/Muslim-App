package com.choubapp.muslimapp;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

public class AdkarMassaa extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout content;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adkar_massaa);

        content=findViewById(R.id.contentlayoutt);
        //load AD
        mAdView = findViewById(R.id.adVieww);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        50,
                        r.getDisplayMetrics()
                );
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                content.setLayoutParams(params);
                params.setMargins(0, px, 0, px);
            }
        });


        ArrayList<Adkar> AdkarMassaaList = new ArrayList<>();
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarmassaa1), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarmassaa2), getString(R.string.time4)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarmassaa3), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarmassaa4), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah3), getString(R.string.time7)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah6), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah7), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarmassaa5), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah9), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarmassaa6), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah11), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah12), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah13), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah14), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah15), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah16), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah17), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah18), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah19), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah20), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah21), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah22), getString(R.string.time10)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah23), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah25), getString(R.string.time100)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah26), getString(R.string.time100)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah28), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarmassaa7), getString(R.string.time1)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah29), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah30), getString(R.string.time3)));
        AdkarMassaaList.add(new Adkar(getString(R.string.adkarsabah31), getString(R.string.time3)));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdkarAdapter(AdkarMassaaList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

}
