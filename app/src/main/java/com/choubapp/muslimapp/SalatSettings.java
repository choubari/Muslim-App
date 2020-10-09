package com.choubapp.muslimapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SalatSettings extends AppCompatActivity {
    static final String THEME_KEY = "Theme";
    int PERMISSION_ID = 44;
    FusedLocationProviderClient mFusedLocationClient;
    double mLongitude, mLatitude;
    String mCity;
    TextView DisplayCity, FajrOff, DuhurOff, AsrOff, MaghribOff,IshaOff;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salat_settings);


        //--------ADS
        mInterstitialAd = new InterstitialAd(this);
        // TO RE-SET
        mInterstitialAd.setAdUnitId(getString(R.string.Interstitial_SalatSettings)); //real
        //mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); //test

        mInterstitialAd.loadAd(new AdRequest.Builder()
                .build());
        mInterstitialAd.setAdListener(new com.google.android.gms.ads.AdListener() {
            @Override
            public void onAdLoaded() {
                mInterstitialAd.show();
                super.onAdLoaded();
            }
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        //getLastLocation();
        initiateData();

    }

    @SuppressLint("SetTextI18n")
    public void initiateData(){
        SharedPreferences salatpref = getSharedPreferences("lastprayertimes", MODE_PRIVATE);
        String GETPrayerCity =salatpref.getString("city","Rabat");
        String GETCountry =salatpref.getString("country","Morocco");
        int fajroff =salatpref.getInt("fajroffset",0);
        int dhuhroff =salatpref.getInt("dhuhroffset",0);
        int asroff =salatpref.getInt("asroffset",0);
        int maghriboff =salatpref.getInt("maghriboffset",0);
        int ishaoff =salatpref.getInt("ishaoffset",0);
        DisplayCity = findViewById(R.id.displayCity);
        FajrOff=findViewById(R.id.fajrcorr);
        DuhurOff=findViewById(R.id.duhurcorr);
        AsrOff=findViewById(R.id.asrcorr);
        MaghribOff=findViewById(R.id.maghrebcorr);
        IshaOff=findViewById(R.id.ishacorr);
        DisplayCity.setText(GETPrayerCity+", "+GETCountry);
        FajrOff.setText(""+fajroff+getString(R.string.minutes));
        DuhurOff.setText(""+dhuhroff+getString(R.string.minutes));
        AsrOff.setText(""+asroff+getString(R.string.minutes));
        MaghribOff.setText(""+maghriboff+getString(R.string.minutes));
        IshaOff.setText(""+ishaoff+getString(R.string.minutes));
    }

    public void setOffsetFajr(View v){
        TextView textView = findViewById(R.id.fajrcorr);
        setOffset("fajroffset",textView);
    }
    public void setOffsetDhuhr(View v){
        TextView textView = findViewById(R.id.duhurcorr);
        setOffset("dhuhroffset",textView);
    }
    public void setOffsetAsr(View v){
        TextView textView = findViewById(R.id.asrcorr);
        setOffset("asroffset",textView);
    }
    public void setOffsetMaghrib(View v){
        TextView textView = findViewById(R.id.maghrebcorr);
        setOffset("maghriboffset",textView);
    }
    public void setOffsetIsha(View v){
        TextView textView = findViewById(R.id.ishacorr);
        setOffset("ishaoffset",textView);
    }


    public void setOffset(final String sharedprefkey , final TextView textview){
        Context mContext =getApplicationContext();
        RelativeLayout linearLayout = new RelativeLayout(mContext);
        //final NumberPicker aNumberPicker = new NumberPicker(mContext);
        //aNumberPicker.setMaxValue(60);
        //aNumberPicker.setMinValue(-60);
        ArrayList<String> offsets =new ArrayList<>();
        for (int i=-60 ; i<=60; i++){offsets.add( " "+i+" "+getString(R.string.minutes)); }
        String[] DisplayedOffsets = offsets.toArray(new String[0]);
        final NumberPicker aNumberPicker = new NumberPicker(mContext);
        aNumberPicker.setMinValue(0);
        aNumberPicker.setMaxValue(120);
        aNumberPicker.setDisplayedValues(DisplayedOffsets);
        aNumberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(50, 50);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        linearLayout.setLayoutParams(params);
        linearLayout.addView(aNumberPicker,numPicerParams);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this,R.style.AlertDialogCustom);
        alertDialogBuilder.setTitle(getString(R.string.manualcorr));
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                SharedPreferences salatpref = getSharedPreferences("lastprayertimes", MODE_PRIVATE);
                                SharedPreferences.Editor editor = salatpref.edit();
                                System.out.println(aNumberPicker.getValue());
                                int nbr =aNumberPicker.getValue() -60;
                                String newtext= ""+nbr+getResources().getString(R.string.minutes);
                                textview.setText(newtext);
                                editor.putInt(sharedprefkey,nbr);
                                editor.apply();
                            }
                        })
                .setNegativeButton(getString(R.string.cancel),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



    public void findLocation(View v){
        getLastLocation();
        Toast.makeText(getApplicationContext(), getString(R.string.locationloading), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    setLatitude(location.getLatitude());
                                    setLongitude(location.getLongitude());
                                    FindCity(location.getLatitude(), location.getLongitude());
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );
    }
    private LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            setLatitude(mLastLocation.getLatitude());
            setLongitude(mLastLocation.getLongitude());
            FindCity(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
    };
    private boolean checkPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
        );
    }
    //----------------------------
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        //if (checkPermissions()) {
          //  getLastLocation();
        //}
    }
    public void FindCity(double MyLat, double MyLong) {
        //double MyLat = 33.97159194946289;
        //double MyLong = -6.849812984466553;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String cityName = addresses.get(0).getLocality();
        String country = addresses.get(0).getCountryName();
        setCity(cityName);
        String cityy= cityName.replace(' ', '-');
        SharedPreferences preff = getSharedPreferences("lastprayertimes", MODE_PRIVATE);
        SharedPreferences.Editor editor = preff.edit();
        editor.putString("country", country);
        editor.putString("city", cityy);
        editor.apply();
        DisplayCity = findViewById(R.id.displayCity);
        DisplayCity.setText(cityy+", "+country);
    }
    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }
    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }
    public void setCity(String city) {
        mCity = city;
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(this,MainActivity.class);
        overridePendingTransition(0, 0);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }


}
