package com.choubapp.muslimapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    static final String THEME_KEY = "Theme";
    ArrayList<String> NotifMessages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NotifMessages();
        scheduleNotification(this,1,6);
        scheduleNotification(this,2,19);
        if(NetworkConnectivity.isNetworkStatusAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getString(R.string.internetfound), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internetlost), Toast.LENGTH_SHORT).show();
        }
    }

    public void NotifMessages(){
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought1)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought2)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought3)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought4)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought5)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought6)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought7)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought8)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought9)+"“");
        NotifMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.thought10)+"“");
    }
    public void scheduleNotification(Context context,int type, int hh){
        Intent broadcastIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        broadcastIntent.addCategory("android.intent.category.DEFAULT");
        broadcastIntent.putStringArrayListExtra("ArrayMessages",NotifMessages);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        switch (type) {
            case 1: { //AdkarSabah
                broadcastIntent.putExtra("NotifTitle1", getString(R.string.menu_sunrise));
                broadcastIntent.putExtra("NotifId1",type);
                broadcastIntent.putExtra("NotifHH1",hh);
                //broadcastIntent.putExtra("NotifMM1",mm);
                PendingIntent broadcast = PendingIntent.getBroadcast(this, 1, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //Time will be get later from Subuh Prayer Time
                cal.set(Calendar.HOUR_OF_DAY,hh);
                //cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),  AlarmManager.INTERVAL_DAY, broadcast);
                break;
            }
            case 2: { //AdkarMassaa
                broadcastIntent.putExtra("NotifTitle2", getString(R.string.menu_sunset));
                broadcastIntent.putExtra("NotifId2",type);
                broadcastIntent.putExtra("NotifHH2",hh);
                //broadcastIntent.putExtra("NotifMM2",mm);
                PendingIntent broadcastt = PendingIntent.getBroadcast(this, 2, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                //Time will be get later from Asr Prayer Time
                cal.set(Calendar.HOUR_OF_DAY, hh);
                //cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcastt);
                break;
            }
        }
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
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
