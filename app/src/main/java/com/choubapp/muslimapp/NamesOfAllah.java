package com.choubapp.muslimapp;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NamesOfAllah extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private TextView mTextView;
    //private RecyclerView.LayoutManager mLayoutManager;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);

        mInterstitialAd = new InterstitialAd(this);
        // TO RE-SET
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test
            mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_Names99));
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
        setContentView(R.layout.activity_names_of_allah);

        //ArrayList<String> Names = new ArrayList<>();
        List<String> names = Arrays.asList( "1", "2", "3","4","5","6","7","8","9","a","b","c","d","e",
                "f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
                "A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U",
                "V","W","X","Y","Z","\"","#","$","%","&","'","(",")","*","+",",","-",".","/","0",":",
                ";","<","=",">","?","@","[","\\","]","^","_","`","{","|","}","~","¡","¢","£","¤","¥","¦","!");


        ArrayList<Names99> AllahNames = new ArrayList<>();
        for (int i =0; i<names.size(); i++){
            AllahNames.add(new Names99(names.get(i)));
        }
        //AllahNames.add(new Names99(getString(R.string.namesList)));

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        //mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Names99Adapter(AllahNames);
        //mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setLayoutManager(new RtlGridLayoutManager(this, 3));
        mRecyclerView.setAdapter(mAdapter);

    }
}
