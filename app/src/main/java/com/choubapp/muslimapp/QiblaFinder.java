package com.choubapp.muslimapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import static android.view.View.INVISIBLE;

public class QiblaFinder extends AppCompatActivity {
    private static final String TAG = "QiblaFinder";
    private Compass compass;
    private ImageView arrowViewQiblat;
    private ImageView imageDial;
    private TextView text_up;
    private TextView text_down;
   public MenuItem item;
    private float currentAzimuth;
    SharedPreferences prefs;
    GPSTracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences preff = getSharedPreferences(MainActivity.THEME_KEY, 0);
        int thm = AboutUs.getCurrentTheme(preff);
        AboutUs.setCurrentTheme(this, thm);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qibla_finder);

        prefs = getSharedPreferences("qibla", MODE_PRIVATE);
        gps = new GPSTracker(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        arrowViewQiblat =  findViewById(R.id.main_image_qibla);
        imageDial =  findViewById(R.id.main_image_dial);
        text_up =  findViewById(R.id.text_up);
        text_down =  findViewById(R.id.text_down);
        arrowViewQiblat.setVisibility(INVISIBLE);
        arrowViewQiblat.setVisibility(View.GONE);

        setupCompass();

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        if(compass != null) {
            compass.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(compass != null) {
            compass.stop();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(compass != null) {
            compass.start();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        if(compass != null) {
            compass.stop();
        }
    }

    private void setupCompass() {
        Boolean permission_granted = GetBoolean("permission_granted");
        if(permission_granted) {
            getBearing();
        }else{
            text_up.setText("");
            text_down.setText(getResources().getString(R.string.permission_not_garanted));
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            }
        }
        compass = new Compass(this);
        Compass.CompassListener cl = new Compass.CompassListener() {
            @Override
            public void onNewAzimuth(float azimuth) { adjustGambarDial(azimuth); adjustArrowQiblat(azimuth); }
        };
        compass.setListener(cl);
    }


    public void adjustGambarDial(float azimuth) {
        Animation an = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentAzimuth = (azimuth);
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        imageDial.startAnimation(an);
    }
    public void adjustArrowQiblat(float azimuth) {
        float QiblaDegree = GetFloat("QiblaDegree");
        Animation an = new RotateAnimation(-(currentAzimuth)+QiblaDegree, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentAzimuth = (azimuth);
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        arrowViewQiblat.startAnimation(an);
        if(QiblaDegree > 0){
            arrowViewQiblat .setVisibility(View.VISIBLE);
        }else{
            arrowViewQiblat .setVisibility(INVISIBLE);
            arrowViewQiblat .setVisibility(View.GONE);
        }
    }

    @SuppressLint("MissingPermission")
    public void getBearing(){
        // Get the location manager

        fetch_GPS();
        /*float QiblaDegree = GetFloat("QiblaDegree");
        if(QiblaDegree > 0.0001){
            text_down.setText(getResources().getString(R.string.coord) +" "+getResources().getString(R.string.lastlocation));
            text_up.setText(getResources().getString(R.string.qibladirection) +" " + QiblaDegree + " " + getResources().getString(R.string.fromnorth));
            arrowViewQiblat .setVisibility(View.VISIBLE);
        }else
        {
            fetch_GPS();
        }*/
    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    SaveBoolean("permission_granted", true);
                    text_down.setText(getResources().getString(R.string.permissiongaranted));
                    arrowViewQiblat.setVisibility(INVISIBLE);
                    arrowViewQiblat.setVisibility(View.GONE);

                } else {
                    finish();
                }
                return;
            }
        }
    }

    public  void SaveBoolean(String key, Boolean bb){
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(key, bb);
        edit.apply();
    }
    public Boolean GetBoolean(String key){
        Boolean result = prefs.getBoolean(key, false);
        return result;
    }
    public void SaveFloat(String key, Float ff){
        SharedPreferences.Editor edit = prefs.edit();
        edit.putFloat(key, ff);
        edit.apply();
    }
    public Float GetFloat(String key){
        Float ff = prefs.getFloat(key, 0);
        return ff;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        fetch_GPS();
        return true;
    }

    public void fetch_GPS(){
        double result = 0;
        gps = new GPSTracker(this);
        if(gps.canGetLocation()){
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            text_down.setText(getResources().getString(R.string.coord)+ "\n"+getResources().getString(R.string.latitude)+": " + latitude + getResources().getString(R.string.longitude)+": " + longitude);
            Log.e("TAG", "GPS is on");
            double lat_saya = gps.getLatitude ();
            double lon_saya = gps.getLongitude ();
            if(lat_saya < 0.001 && lon_saya < 0.001) {
                arrowViewQiblat.setVisibility(INVISIBLE);
                arrowViewQiblat.setVisibility(View.GONE);
                text_up.setText("");
                text_down.setText(getResources().getString(R.string.locationunready));
            }else{
                double longitude2 = 39.826209; // Kaabah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                double longitude1 = lon_saya;
                double latitude2 = Math.toRadians(21.422507); // Kaabah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                double latitude1 = Math.toRadians(lat_saya);
                double longDiff= Math.toRadians(longitude2-longitude1);
                double y= Math.sin(longDiff)*Math.cos(latitude2);
                double x=Math.cos(latitude1)*Math.sin(latitude2)-Math.sin(latitude1)*Math.cos(latitude2)*Math.cos(longDiff);
                result = (Math.toDegrees(Math.atan2(y, x))+360)%360;
                float result2 = (float)result;
                SaveFloat("QiblaDegree", result2);
                text_up.setText(getResources().getString(R.string.qibladirection) +" "+ result2 + " "+ getResources().getString(R.string.fromnorth));
                arrowViewQiblat .setVisibility(View.VISIBLE);

            }
        }else{
            gps.showSettingsAlert();
            arrowViewQiblat.setVisibility(INVISIBLE);
            arrowViewQiblat.setVisibility(View.GONE);
            text_up.setText("");
            text_down.setText(getResources().getString(R.string.gpsplz));
        }
    }

    public void QiblaTips(View v){
        final Context mContext=this;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext,R.style.AlertDialogCustom);
        alertDialog.setTitle(mContext.getResources().getString(R.string.consignes));
        alertDialog.setMessage(mContext.getResources().getString(R.string.qiblatips));
        alertDialog.setPositiveButton(mContext.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

}
