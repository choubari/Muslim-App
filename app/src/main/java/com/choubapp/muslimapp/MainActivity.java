package com.choubapp.muslimapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    static final String THEME_KEY = "Theme";
    ArrayList<String> NotifMessages = new ArrayList<>();
    ArrayList<String> NotifSalatMessages = new ArrayList<>();
   // private ArrayList<String> mPrayerTimes,mRetrievedPrayerTimes;
    TextView mFajr,mDuhur,mAsr,mMaghrib,mIsha,mCity;
    ScrollView content;
    private AdView mAdView;

    //String PrayerCity = "kenitra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        content=findViewById(R.id.scrollscreen);
        NotifMessages();
        SharedPreferences preff = getSharedPreferences("prefs", MODE_PRIVATE);
        boolean firstStart = preff.getBoolean("firstStartDialog", true);
        if (firstStart) showStartDialog();

        if(NetworkConnectivity.isNetworkStatusAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getString(R.string.internetfound), Toast.LENGTH_SHORT).show();
            new GetPrayerTimes().execute();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internetlost), Toast.LENGTH_SHORT).show();
            LoadPreviousSalatData();
        }

        mAdView = findViewById(R.id.adViewmain);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        mAdView.loadAd(adRequest);
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Resources r = getResources();
                int px = (int) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        50,
                        r.getDisplayMetrics()
                );
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                );
                content.setLayoutParams(params);
                params.setMargins(0, 0, 0, px);
            }
            @Override
            public void onAdFailedToLoad(int errorCode) {
                Log.d("onAdFailedToLoad", "This is why: "+errorCode);
            }
        });

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
        NotifMessages.add(getString(R.string.AdkarExtra1));
        NotifMessages.add(getString(R.string.AdkarExtra2));
        NotifMessages.add(getString(R.string.AdkarExtra3));
        NotifMessages.add(getString(R.string.AdkarExtra4));
        NotifMessages.add(getString(R.string.AdkarExtra5));
        NotifMessages.add(getString(R.string.AdkarExtra6));
        NotifSalatMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.SalatThought1)+"“");
        NotifSalatMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.SalatThought2)+"“");
        NotifSalatMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.SalatThought3)+"“");
        NotifSalatMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.SalatThought4)+"“");
        NotifSalatMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.SalatThought5)+"“");
        NotifSalatMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.SalatThought6)+"“");
        NotifSalatMessages.add(getString(R.string.menu_fadlGod1)+" ”"+getString(R.string.SalatThought7)+"“");
        NotifSalatMessages.add(getString(R.string.SalatExtra1));
        NotifSalatMessages.add(getString(R.string.SalatExtra2));
        NotifSalatMessages.add(getString(R.string.SalatExtra3));
        NotifSalatMessages.add(getString(R.string.SalatExtra4));
        NotifSalatMessages.add(getString(R.string.SalatExtra5));
    }
    public  int GetRandom(){
        Random rng = new Random();
        int random = rng.nextInt(1000000);
        return  random;
    }

    public void scheduleNotification(Context context,int type, int hh, int mm ){
        Intent broadcastIntent = new Intent("android.media.action.DISPLAY_NOTIFICATION");
        broadcastIntent.addCategory("android.intent.category.DEFAULT");
        broadcastIntent.putStringArrayListExtra("ArrayMessages",NotifMessages);
        broadcastIntent.putStringArrayListExtra("ArraySalatMessages",NotifSalatMessages);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        String HH =String.format("%02d", hh);
        String MM=String.format("%02d", mm);
        switch (type) {
            case 10: { //AdkarSabah
                broadcastIntent.putExtra("NotifTitle10", getString(R.string.menu_sunrise));
                broadcastIntent.putExtra("NotifId10",type);
                broadcastIntent.putExtra("NotifTimeMM10",mm);
                broadcastIntent.putExtra("NotifTimeHH10",hh);
                PendingIntent broadcast10 = PendingIntent.getBroadcast(this,10, broadcastIntent ,PendingIntent.FLAG_UPDATE_CURRENT);
                //Time will be get later from Subuh Prayer Time
                cal.set(Calendar.HOUR_OF_DAY,hh);
                cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(),  AlarmManager.INTERVAL_DAY, broadcast10);
                break;
            }
            case 11: { //AdkarMassaa
                broadcastIntent.putExtra("NotifTitle11", getString(R.string.menu_sunset));
                broadcastIntent.putExtra("NotifId11",type);
                broadcastIntent.putExtra("NotifTimeMM11",mm);
                broadcastIntent.putExtra("NotifTimeHH11",hh);
                PendingIntent broadcast11 = PendingIntent.getBroadcast(this, 11, broadcastIntent ,PendingIntent.FLAG_UPDATE_CURRENT);
                //Time will be get later from Asr Prayer Time
                cal.set(Calendar.HOUR_OF_DAY, hh);
                cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast11);
                break;
            }
            case 1: { //Fajr
                String message = getString(R.string.itsprayertime)+" "+getString(R.string.fajr)+" "+HH+":"+MM ;
                broadcastIntent.putExtra("NotifTitle1",  message);
                broadcastIntent.putExtra("NotifId1",type);
                broadcastIntent.putExtra("NotifTimeMM1",mm);
                broadcastIntent.putExtra("NotifTimeHH1",hh);
                PendingIntent broadcast1 = PendingIntent.getBroadcast(this, 1, broadcastIntent ,PendingIntent.FLAG_UPDATE_CURRENT);
                cal.set(Calendar.HOUR_OF_DAY, hh);
                cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast1);
                break;
            }
            case 2: { //Dhuhr
                String message = getString(R.string.itsprayertime)+" "+getString(R.string.dohr)+" "+HH+":"+MM ;
                broadcastIntent.putExtra("NotifTitle2",  message);
                broadcastIntent.putExtra("NotifId2",type);
                broadcastIntent.putExtra("NotifTimeMM2",mm);
                broadcastIntent.putExtra("NotifTimeHH2",hh);
                PendingIntent broadcast2 = PendingIntent.getBroadcast(this,2, broadcastIntent ,PendingIntent.FLAG_UPDATE_CURRENT);
                cal.set(Calendar.HOUR_OF_DAY, hh);
                cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast2);
                break;
            }
            case 3: { //Asr
                String message = getString(R.string.itsprayertime)+" "+getString(R.string.asr)+" "+HH+":"+MM  ;
                broadcastIntent.putExtra("NotifTitle3",  message);
                broadcastIntent.putExtra("NotifId3",type);
                broadcastIntent.putExtra("NotifTimeMM3",mm);
                broadcastIntent.putExtra("NotifTimeHH3",hh);
                PendingIntent broadcast3 = PendingIntent.getBroadcast(this, 3, broadcastIntent ,PendingIntent.FLAG_UPDATE_CURRENT);
                cal.set(Calendar.HOUR_OF_DAY, hh);
                cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast3);
                break;
            }
            case 4: { //Maghrib
                String message = getString(R.string.itsprayertime)+" "+getString(R.string.maghrib)+" "+HH+":"+MM ;
                broadcastIntent.putExtra("NotifTitle4",  message);
                broadcastIntent.putExtra("NotifId4",type);
                broadcastIntent.putExtra("NotifTimeMM4",mm);
                broadcastIntent.putExtra("NotifTimeHH4",hh);
                PendingIntent broadcast4 = PendingIntent.getBroadcast(this,4, broadcastIntent ,PendingIntent.FLAG_UPDATE_CURRENT);
                cal.set(Calendar.HOUR_OF_DAY, hh);
                cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast4);
                break;
            }
            case 5: { //Isha
                String message = getString(R.string.itsprayertime)+" "+getString(R.string.isha)+" "+HH+":"+MM ;
                broadcastIntent.putExtra("NotifTitle5",  message);
                broadcastIntent.putExtra("NotifId5",type);
                broadcastIntent.putExtra("NotifTimeMM5",mm);
                broadcastIntent.putExtra("NotifTimeHH5",hh);
                PendingIntent broadcast5 = PendingIntent.getBroadcast(this, 5, broadcastIntent ,PendingIntent.FLAG_UPDATE_CURRENT);
                cal.set(Calendar.HOUR_OF_DAY, hh);
                cal.set(Calendar.MINUTE,mm);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), AlarmManager.INTERVAL_DAY, broadcast5);
                break;
            }

        }
    }

    /*public void RefreshPrayerTimes(View v){
        if(NetworkConnectivity.isNetworkStatusAvailable(getApplicationContext())) {
            new GetPrayerTimes().execute();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internetfirst), Toast.LENGTH_SHORT).show();
        }
    }*/

    public void SalatSettings(View v){
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
    public void namesofAllah(View v){
        Intent intent= new Intent(this,NamesOfAllah.class);
        startActivity(intent);
    }
    public void restofdikr(View v){
        Intent intent= new Intent(this,RestOfDikr.class);
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
    public void shareApp(View v){
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage= "\n حصن المسلم اليومي - تطبيق يضم العديد من الخصائص منها أوقات الصلوات، اتجاه القبلة، مسبحة إلكترونية، أذكار الصباح و المساء و يشجع المسلم على مداومتها\n\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            Toast.makeText(getBaseContext(), "خلل في مشاركة التطبيق، المرجو الإعادة", Toast.LENGTH_SHORT);
            //e.toString();
        }
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
                .setTitle(getString(R.string.hello))
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
        TextView mxFajr,mxDuhur,mxAsr,mxMaghrib,mxIsha,mxCity;
        mxFajr=findViewById(R.id.fajr);
        mxDuhur=findViewById(R.id.duhur);
        mxAsr=findViewById(R.id.asr);
        mxMaghrib=findViewById(R.id.maghrib);
        mxIsha=findViewById(R.id.isha);
        mxCity=findViewById(R.id.city);
        String GETPrayerCity =salatpref.getString("city",getString(R.string.city));
        String GETfajr = salatpref.getString("fajr","00:00");
        String GETduhur = salatpref.getString("duhur","00:00");
        String GETasr = salatpref.getString("asr","00:00");
        String GETmaghrib = salatpref.getString("maghrib","00:00");
        String GETisha = salatpref.getString("isha","00:00");

        mxCity.setText(GETPrayerCity);
        mxFajr.setText(GETfajr);
        mxDuhur.setText(GETduhur);
        mxAsr.setText(GETasr);
        mxMaghrib.setText(GETmaghrib);
        mxIsha.setText(GETisha);
    }

    private class GetPrayerTimes extends AsyncTask<Void, Void, Void> {
        SharedPreferences salatpref = getSharedPreferences("lastprayertimes", MODE_PRIVATE);
        String GETPrayerCity =salatpref.getString("city","Rabat");
        String GETCountry =salatpref.getString("country","Morocco");
        int fajroff =salatpref.getInt("fajroffset",0);
        int dhuhroff =salatpref.getInt("dhuhroffset",0);
        int asroff =salatpref.getInt("asroffset",0);
        int maghriboff =salatpref.getInt("maghriboffset",0);
        int ishaoff =salatpref.getInt("ishaoffset",0);
        String fajr,duhur,asr,maghrib,isha;
        Boolean Passed=false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //Toast.makeText(MainActivity.this,getString(R.string.loadingprayertimes),Toast.LENGTH_LONG).show();
        }
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            //String url = "https://api.pray.zone/v2/times/today.json?city="+GETPrayerCity;
            String offsets = "&tune=0,"+(fajroff-6)+",0,"+(dhuhroff+5)+","+asroff+","+(maghriboff+5)+",0,"+ishaoff+",0" ;
            String url ="http://api.aladhan.com/v1/timingsByCity?city="+GETPrayerCity+"&country="+GETCountry+"&method=3"+offsets;
            String jsonStr = sh.makeServiceCall(url);
            //Log.e("TAG", "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONObject data = jsonObj.getJSONObject("data");
                    JSONObject prayertimes = data.getJSONObject("timings");
                    fajr = prayertimes.getString("Fajr");
                    duhur = prayertimes.getString("Dhuhr");
                    asr = prayertimes.getString("Asr");
                    maghrib = prayertimes.getString("Maghrib");
                    isha = prayertimes.getString("Isha");
                    Passed =true;
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
           // editor.putString("city", GETPrayerCity);
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
            mCity.setText(GETPrayerCity);
            mFajr.setText(fajr);
            mDuhur.setText(duhur);
            mAsr.setText(asr);
            mMaghrib.setText(maghrib);
            mIsha.setText(isha);
            if (Passed) {
                String[] time1 = fajr.split(":");
                int hh1 = Integer.parseInt(time1[0].trim());
                int mm1 = Integer.parseInt(time1[1].trim());
                String[] time2 = duhur.split(":");
                int hh2 = Integer.parseInt(time2[0].trim());
                int mm2 = Integer.parseInt(time2[1].trim());
                String[] time3 = asr.split(":");
                int hh3 = Integer.parseInt(time3[0].trim());
                int mm3 = Integer.parseInt(time3[1].trim());
                String[] time4 = maghrib.split(":");
                int hh4 = Integer.parseInt(time4[0].trim());
                int mm4 = Integer.parseInt(time4[1].trim());
                String[] time5 = isha.split(":");
                int hh5 = Integer.parseInt(time5[0].trim());
                int mm5 = Integer.parseInt(time5[1].trim());

                scheduleNotification(getApplicationContext(), 10, hh1, mm1);
                scheduleNotification(getApplicationContext(), 11, hh3, mm3);
                scheduleNotification(getApplicationContext(), 1, hh1, mm1);
                scheduleNotification(getApplicationContext(), 2, hh2, mm2);
                scheduleNotification(getApplicationContext(), 3, hh3, mm3);
                scheduleNotification(getApplicationContext(), 4, hh4, mm4);
                scheduleNotification(getApplicationContext(), 5, hh5, mm5);
            }
        }
    }

}
