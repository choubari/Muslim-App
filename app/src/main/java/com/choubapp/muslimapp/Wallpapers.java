package com.choubapp.muslimapp;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Wallpapers extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private ArrayList<ImageItem> mImageList, mImageListt;
    private RequestQueue mRequestQueue;
    WallpaperManager mWallpaperManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences(MainActivity.THEME_KEY,0);
        int thm=AboutUs.getCurrentTheme(prefs);
        AboutUs.setCurrentTheme(this, thm);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpapers);
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        mImageList = new ArrayList<>();
        mImageListt = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        if(NetworkConnectivity.isNetworkStatusAvialable (getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getString(R.string.internetfound), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internetlost), Toast.LENGTH_SHORT).show();
        }
        makelist();
    }
    @SuppressLint("ResourceType")
    public void UpdateWallpaper(View v){
        mWallpaperManager= WallpaperManager.getInstance(getApplicationContext());
        //  mWallpaperManager.setResource();

    }
    private void makelist(){
        if (mImageListt!=null) mImageListt.clear();

        for (int i=30;i>0;i--) {
            mImageListt.add(new ImageItem("https://source.unsplash.com/user/choubari/likes/?sig=" + i));
        }
        mImageAdapter = new ImageAdapter(Wallpapers.this, mImageListt);
        mRecyclerView.setAdapter(mImageAdapter);

    }

}
