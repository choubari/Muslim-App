package com.choubapp.muslimapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class AboutUs extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.Editor mEditor;
    int x;
    static final String THEME_KEY = "Theme";
    Switch switchMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        prefs = getSharedPreferences(THEME_KEY,0);
        x=getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, x);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        setSwitch(x);
    }
    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,MainActivity.class);

        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }

    public static int getCurrentTheme(SharedPreferences pref){
        int t = pref.getInt("Theme", R.style.AppThemee);
        return t;
    }
    public static void setCurrentTheme(Activity activity,int x){
        switch (x){
            case R.style.AppTheme:
                activity.setTheme(R.style.AppTheme);
                break;
            default:
                activity.setTheme(R.style.AppThemee);
                break;
        }
    }
    public  void setSwitch(int x){
        switchMode=findViewById(R.id.switchnightmode);
        switch (x){
            case R.style.AppTheme: switchMode.setChecked(true);
                this.setTheme(R.style.AppTheme);
                break;
            default: switchMode.setChecked(false);
                this.setTheme(R.style.AppThemee);
                break;
        }
    }
    public void SwitchedtoNightMode(View v){
        switchMode=findViewById(R.id.switchnightmode);
        prefs = getSharedPreferences(THEME_KEY,0);
        mEditor = prefs.edit();

        Intent intent = getIntent();
        if (switchMode.isChecked()){
            mEditor.putInt("Theme",R.style.AppTheme);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }else{
            mEditor.putInt("Theme",R.style.AppThemee);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }
        mEditor.commit();
    }
    public void privacypolicy(View v) {
        Intent intent = new Intent(this, PrivacyPolicy.class);
        startActivity(intent);
    }
    public  void reportProblem(View v){
        Intent intent = new Intent(this, ReportProblem.class);
        startActivity(intent);
    }
    public void RateApp (View v){
        try {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + getPackageName())));
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }


}
