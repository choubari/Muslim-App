package com.choubapp.muslimapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ZakatCalculator extends AppCompatActivity {
    int k=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zakat_calculator);
    }



    public void ZakatInfo(View v){
        k=1;
        setContentView(R.layout.zakatinfo);
    }
    @Override
    public void onBackPressed() {
        if (k == 1) {
            k = 0;
            //super.onBackPressed();
            setContentView(R.layout.activity_zakat_calculator);

        } else {
            super.onBackPressed();
        }
    }
}
