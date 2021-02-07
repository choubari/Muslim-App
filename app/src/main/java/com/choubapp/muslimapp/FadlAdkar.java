package com.choubapp.muslimapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

public class FadlAdkar extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);

        mInterstitialAd = new InterstitialAd(this);
        // TO RE-SET
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_FadlAdkar));
        mInterstitialAd.loadAd(new AdRequest.Builder()
                .build());
        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();
            }
        });

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fadl_adkar);
        ArrayList<Fadl> FadlAdkarList = new ArrayList<>();
        FadlAdkarList.add(new Fadl(getString(R.string.thought1), getString(R.string.menu_fadlGod1)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought2), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought3), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought4), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought5), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought6), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought7), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought8), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought9), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought10), getString(R.string.menu_fadlGod2)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought11), getString(R.string.menu_fadlProphet)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought12), getString(R.string.menu_fadlProphet)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought13), getString(R.string.menu_fadlProphet)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought14), getString(R.string.menu_fadlProphet)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought15), getString(R.string.menu_fadlProphet)));
        FadlAdkarList.add(new Fadl(getString(R.string.thought16), getString(R.string.menu_fadlProphet)));


        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new FadlAdapter(FadlAdkarList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
