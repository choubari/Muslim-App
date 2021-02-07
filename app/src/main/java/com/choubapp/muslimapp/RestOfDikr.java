package com.choubapp.muslimapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class RestOfDikr extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);

        mInterstitialAd = new InterstitialAd(this);
        // TO RE-SET
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_RestOfDikr));
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
        setContentView(R.layout.activity_rest_of_dikr);
    }
    public void salat(View v){
        Intent intent= new Intent(this,SalatDikrActivity.class);
        startActivity(intent);
    }
    public void adhan(View v){
        Intent intent= new Intent(this,AdhanDikrActivity.class);
        startActivity(intent);
    }
    public void mosque(View v){
        Intent intent= new Intent(this,MosqueDikrActivity.class);
        startActivity(intent);
    }
    public void ablution(View v){
        Intent intent= new Intent(this,WuduuDikrActivity.class);
        startActivity(intent);
    }
    public void food(View v){
        Intent intent= new Intent(this,FoodDikrActivity.class);
        startActivity(intent);
    }
    public void travel(View v){
        Intent intent= new Intent(this,TravelDikrActivity.class);
        startActivity(intent);
    }
    public void hajjomra(View v){
        Intent intent= new Intent(this,HajjOmraDikrActivity.class);
        startActivity(intent);
    }
    public void home(View v){
        Intent intent= new Intent(this,HomeDikrActivity.class);
        startActivity(intent);
    }
    public void sickness(View v){
        Intent intent= new Intent(this,SickDikrActivity.class);
        startActivity(intent);
    }
    public void nature(View v){
        Intent intent= new Intent(this,NatureDikrActivity.class);
        startActivity(intent);
    }
}
