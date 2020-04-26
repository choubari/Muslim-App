package com.choubapp.muslimapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class QiblaFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY, 0);
        int thm = AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qibla_finder);


        /*double MyLat=	33.97159194946289;
        double MyLong= -6.849812984466553;
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(MyLat, MyLong, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String cityName = addresses.get(0).getLocality();
        System.out.println("city: "+cityName);*/

        // check if GPS enabled

        /*
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            System.out.println("if");

        } else {
            System.out.println("else1");
            GPSTracker gpsTracker = new GPSTracker(this);
            System.out.println("else2");

            if (gpsTracker.getIsGPSTrackingEnabled()) {
                String stringLatitude = String.valueOf(gpsTracker.latitude);

                String stringLongitude = String.valueOf(gpsTracker.longitude);

                String country = gpsTracker.getCountryName(this);

                String city = gpsTracker.getLocality(this);

                String postalCode = gpsTracker.getPostalCode(this);

                String addressLine = gpsTracker.getAddressLine(this);

                System.out.println(stringLatitude);
                System.out.println(stringLongitude);
                System.out.println(country);
                System.out.println(city);
                System.out.println(postalCode);
                System.out.println(addressLine);

            } else {
                // can't get location
                // GPS or Network is not enabled
                // Ask user to enable GPS/network in settings
                gpsTracker.showSettingsAlert();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.varna_lab_geo_locations, menu);
        return true;
    }

    */

    }
}
