package com.choubapp.muslimapp;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SalatSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salat_settings);
    }
}
