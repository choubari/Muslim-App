package com.choubapp.muslimapp;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }



    public void salatsettings(View v){
        Intent intent= new Intent(this,SalatSettings.class);
        startActivity(intent);
    }
    public void adkarsabah(View v){
        Intent intent= new Intent(this,AdkarSabah.class);
        startActivity(intent);
    }
    public void adkarmassaa(View v){
        Intent intent= new Intent(this,AdkarMassaa.class);
        startActivity(intent);
    }
    public void fadladkar(View v){
        Intent intent= new Intent(this,FadlAdkar.class);
        startActivity(intent);
    }
    public void missbaha(View v){
        Intent intent= new Intent(this,Misbaha.class);
        startActivity(intent);
    }
    public void qibla(View v){
        Intent intent= new Intent(this,QiblaFinder.class);
        startActivity(intent);
    }
    public void zakat(View v){
        Intent intent= new Intent(this,ZakatCalculator.class);
        startActivity(intent);
    }
    public void wallpapers(View v){
        Intent intent= new Intent(this,Wallpapers.class);
        startActivity(intent);
    }
    public void widgets(View v){
        Intent intent= new Intent(this,Widgets.class);
        startActivity(intent);
    }
    public void aboutus(View v){
        Intent intent= new Intent(this,AboutUs.class);
        startActivity(intent);
    }
}
