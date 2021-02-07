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

public class SalatDikrActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_salat_dikr);

        content=findViewById(R.id.contentlayout);
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

        ArrayList<Adkar> DikrAfterSalat = new ArrayList<>();
        DikrAfterSalat.add(new Adkar(getString(R.string.salat1), getString(R.string.time1)));
        DikrAfterSalat.add(new Adkar(getString(R.string.salat2), getString(R.string.time1)));
        DikrAfterSalat.add(new Adkar(getString(R.string.salat3), getString(R.string.time1)));
        DikrAfterSalat.add(new Adkar(getString(R.string.salat4), getString(R.string.time33)));

        DikrAfterSalat.add(new Adkar(getString(R.string.adkarsabah25), getString(R.string.time1)));
        DikrAfterSalat.add(new Adkar(getString(R.string.adkarsabah29), getString(R.string.time3e)));
        DikrAfterSalat.add(new Adkar(getString(R.string.adkarsabah30), getString(R.string.time3e)));
        DikrAfterSalat.add(new Adkar(getString(R.string.adkarsabah31), getString(R.string.time3e)));
        DikrAfterSalat.add(new Adkar(getString(R.string.adkarsabah28), getString(R.string.time1)));

        DikrAfterSalat.add(new Adkar(getString(R.string.salat5), getString(R.string.time1e)));
        DikrAfterSalat.add(new Adkar(getString(R.string.salat6), getString(R.string.time7e)));
        DikrAfterSalat.add(new Adkar(getString(R.string.salat7), getString(R.string.time1)));


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new AdkarAdapter(DikrAfterSalat);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
