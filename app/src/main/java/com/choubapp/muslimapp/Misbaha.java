package com.choubapp.muslimapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Misbaha extends AppCompatActivity {
    TextView showcounter;
    int count;
    private AdView mAdView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_misbaha);
        mAdView = findViewById(R.id.adViewww);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        SharedPreferences sp = getSharedPreferences("tallycounter", Activity.MODE_PRIVATE);
        int myIntValue = sp.getInt("counter", 0);
        showcounter=findViewById(R.id.counter_text);
        showcounter.setText(""+myIntValue);
    }
    public void reset(View v){
        showcounter=findViewById(R.id.counter_text);
        showcounter.setText("0");
        SharedPreferences sp = getSharedPreferences("tallycounter", Misbaha.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("counter", 0);
        editor.commit();
    }
    public void counter (View v){
        showcounter=findViewById(R.id.counter_text);
        count= 1 + Integer.parseInt((String) showcounter.getText()) ;
        showcounter.setText(""+count);
        SharedPreferences sp = getSharedPreferences("tallycounter", Misbaha.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("counter", count);
        editor.commit();
    }
}
