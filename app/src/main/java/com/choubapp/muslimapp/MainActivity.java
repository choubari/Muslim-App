package com.choubapp.muslimapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends AppCompatActivity {
    static final String THEME_KEY = "Theme";
    ArrayList<String> NotifMessages = new ArrayList<>();
   // private ArrayList<String> mPrayerTimes,mRetrievedPrayerTimes;
    TextView mFajr,mDuhur,mAsr,mMaghrib,mIsha,mCity;
    String fajr,duhur,asr,maghrib,isha;
    String PrayerCity = "kenitra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NotifMessages();
        SharedPreferences preff = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = preff.getBoolean("firstStartDialog", true);
        if (firstStart) showStartDialog();

        scheduleNotification(this,1,5);
        scheduleNotification(this,2,17);

        //mPrayerTimes= new ArrayList<>();
        //mPrayerTimes.add(0,PrayerCity);

        if(NetworkConnectivity.isNetworkStatusAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getString(R.string.internetfound), Toast.LENGTH_SHORT).show();
            new GetPrayerTimes().execute();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internetlost), Toast.LENGTH_SHORT).show();
            LoadPreviousSalatData();
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
                cal.set(Calendar.MINUTE,05);
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
                cal.set(Calendar.MINUTE,05);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcastt);
                break;
            }
        }
    }

    public void RefreshPrayerTimes(View v){
        if(NetworkConnectivity.isNetworkStatusAvailable(getApplicationContext())) {
            new GetPrayerTimes().execute();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internetfirst), Toast.LENGTH_SHORT).show();
        }
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

    private long backPressedTime;
    private Toast backToast;
    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), getString(R.string.clickbackagaintoexit), Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
    private void showStartDialog() {
         new AlertDialog.Builder(this,R.style.AlertDialogCustom)
                .setTitle(getString(R.string.ramadankareem))
                .setMessage(getString(R.string.firstalertdialogmsg))
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
        SharedPreferences preff = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preff.edit();
        editor.putBoolean("firstStartDialog", false);
        editor.apply();
    }

    public void LoadPreviousSalatData(){
        SharedPreferences salatpref = getSharedPreferences("lastprayertimes", MODE_PRIVATE);
        System.out.println("good1");
        TextView mxFajr,mxDuhur,mxAsr,mxMaghrib,mxIsha,mxCity;
        mxFajr=findViewById(R.id.fajr);
        mxFajr.setText("hhmm");
        mxDuhur=findViewById(R.id.duhur);
        mxAsr=findViewById(R.id.asr);
        mxMaghrib=findViewById(R.id.maghrib);
        mxIsha=findViewById(R.id.isha);
        mxCity=findViewById(R.id.city);
        System.out.println("good1");
        String GETPrayerCity =salatpref.getString("city",getString(R.string.location));
        String GETfajr = salatpref.getString("fajr","00:00");
        String GETduhur = salatpref.getString("duhur","00:00");
        String GETasr = salatpref.getString("asr","00:00");
        String GETmaghrib = salatpref.getString("maghrib","00:00");
        String GETisha = salatpref.getString("isha","00:00");
        System.out.println("good1" + GETPrayerCity);

        mxCity.setText(GETPrayerCity);
        mxFajr.setText(GETfajr);
        mxDuhur.setText(GETduhur);
        mxAsr.setText(GETasr);
        mxMaghrib.setText(GETmaghrib);
        mxIsha.setText(GETisha);
    }

    private class GetPrayerTimes extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,getString(R.string.loadingprayertimes),Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://api.pray.zone/v2/times/today.json?city="+PrayerCity;
            String jsonStr = sh.makeServiceCall(url);
            //Log.e("TAG", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject result = jsonObj.getJSONObject("results");
                    System.out.println("result"+result);
                    JSONArray data = result.getJSONArray("datetime");
                    System.out.println("data"+data);
                    JSONObject timings = data.getJSONObject(0);
                    JSONObject prayertimes = timings.getJSONObject("times");
                    fajr = prayertimes.getString("Fajr");
                    duhur = prayertimes.getString("Dhuhr");
                    asr = prayertimes.getString("Asr");
                    maghrib = prayertimes.getString("Maghrib");
                    isha = prayertimes.getString("Isha");

                } catch (final JSONException e) {
                    Log.e("TAG", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), getString(R.string.errorloadprayertimes), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } else {
                Log.e("TAG", "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), getString(R.string.errorloadprayertimes), Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), getString(R.string.prayertimesloaded), Toast.LENGTH_LONG).show();
            SharedPreferences salatpref = getSharedPreferences("lastprayertimes", MODE_PRIVATE);
            SharedPreferences.Editor editor = salatpref.edit();
            editor.putString("city", PrayerCity);
            editor.putString("fajr",fajr);
            editor.putString("duhur",duhur);
            editor.putString("asr",asr);
            editor.putString("maghrib",maghrib);
            editor.putString("isha",isha);
            editor.apply();

            mFajr=findViewById(R.id.fajr);
            mDuhur=findViewById(R.id.duhur);
            mAsr=findViewById(R.id.asr);
            mMaghrib=findViewById(R.id.maghrib);
            mIsha=findViewById(R.id.isha);
            mCity=findViewById(R.id.city);
            mCity.setText(PrayerCity);
            mFajr.setText(fajr);
            mDuhur.setText(duhur);
            mAsr.setText(asr);
            mMaghrib.setText(maghrib);
            mIsha.setText(isha);

        }
    }

}
