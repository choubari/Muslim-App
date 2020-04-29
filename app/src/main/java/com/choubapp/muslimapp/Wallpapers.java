package com.choubapp.muslimapp;

import android.app.WallpaperManager;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class Wallpapers extends AppCompatActivity implements ImageAdapter.OnItemClickListener, WallpaperDialog.WallpaperDialogListener{
    private RecyclerView mRecyclerView;
    private ImageAdapter mImageAdapter;
    private ArrayList<ImageItem> mImageListt;
    private RequestQueue mRequestQueue;
    ProgressBar progressload;
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
        mImageListt = new ArrayList<>();
        if(NetworkConnectivity.isNetworkStatusAvailable(getApplicationContext())) {
            Toast.makeText(getApplicationContext(), getString(R.string.internetfound), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), getString(R.string.internetlost), Toast.LENGTH_SHORT).show();
        }
        mRequestQueue = Volley.newRequestQueue(this);
        progressload= findViewById(R.id.progressBar);
        parseJSON();
    }

    public ArrayList<Integer> randomUnique(int max){
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i=1; i<max; i++) {
            list.add(new Integer(i));
        }
        Collections.shuffle(list);
        return list;
    }

    private void parseJSON() {
        String url = "https://api.npoint.io/e99d032bbebe11c2e76f";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("pics");
                            ArrayList<Integer> list = randomUnique(jsonArray.length());
                            for (int i = 0; i < 20; i++) {
                                JSONObject hit = jsonArray.getJSONObject(list.get(i));
                                String imageUrl = hit.getString("imageurl");
                                mImageListt.add(new ImageItem(imageUrl));
                            }
                            mImageAdapter = new ImageAdapter(Wallpapers.this, mImageListt);
                            mRecyclerView.setAdapter(mImageAdapter);
                            mImageAdapter.setOnItemClickListener(Wallpapers.this);
                            progressload.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }


    @Override
    public void onItemClick(int position) {
        openDialog(position);
    }
    int getposition;

    //making target global variable actually make difference to handle onBitmapLoaded correctly (it wasnt called on first load also on odd number of loads)
    Target target;
    @Override
    public void onYesClicked() {
        ImageItem clickedItem = mImageListt.get(getposition);
        final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
         target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    wallpaperManager.setBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.wallpaperset), Toast.LENGTH_SHORT).show();
                } catch (IOException ex) {
                    ex.printStackTrace();
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.wallpapertryagain), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Toast.makeText(getApplicationContext(),  getResources().getString(R.string.wallpaperfailed), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Toast.makeText(getApplicationContext(),  getResources().getString(R.string.wallpaperloading), Toast.LENGTH_SHORT).show();
            }
        };
        String url =clickedItem.getImageUrl();
        Picasso.get().load(url).resize(1080, 1920).centerCrop().into(target);
    }
    public void openDialog(int p) {
        WallpaperDialog dialog = new WallpaperDialog();
        dialog.show(getSupportFragmentManager(), "Wallpaper Dialog");
        getposition=p;
    }

}
