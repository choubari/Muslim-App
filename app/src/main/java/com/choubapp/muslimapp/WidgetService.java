package com.choubapp.muslimapp;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

public class WidgetService  extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetItemFactory(getApplicationContext(), intent);

    }

    class WidgetItemFactory implements RemoteViewsFactory {
        private Context context;
        private int appWidgetId;
        ArrayList<Adkar> AdkarWidgetList;

        WidgetItemFactory(Context context, Intent intent) {
            this.context = context;
            this.appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {
            //connect to data source
            SystemClock.sleep(1000);
            SharedPreferences sharedPreferences = getSharedPreferences("AdkarWidgetList", MODE_PRIVATE);
            Gson gson = new Gson();
            String json = sharedPreferences.getString("AdkarList", null);
            Type type = new TypeToken<ArrayList<Adkar>>() {}.getType();
            AdkarWidgetList = gson.fromJson(json, type);
            if (AdkarWidgetList== null) {
                AdkarWidgetList= new ArrayList<Adkar>();
                Toast.makeText(context, "Widget Menu to activate", Toast.LENGTH_SHORT).show();
            }

        }


        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {
            //close data source
        }

        @Override
        public int getCount() {
           return AdkarWidgetList.size();
        }

        @SuppressLint("ResourceAsColor")
        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_item);
            views.setTextViewText(R.id.widget_item_text,AdkarWidgetList.get(position).getDouaa());
            views.setTextViewText(R.id.widget_item_times,AdkarWidgetList.get(position).getTimes());
            /*if (AdkarWidgetList.get(position).getDouaa().equals(getString(R.string.menu_sunrise))){
                //views.setTextColor(R.id.widget_item_text,R.color.colorAccent);
                views.setTextViewTextSize(R.id.widget_item_text,COMPLEX_UNIT_SP,24);

            }*/
           /* Bundle extras = new Bundle();
            extras.putInt(WidgetProvider.NEXT_ACTION, position);
            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extras);
            // Make it possible to distinguish the individual on-click
            // action of a given item
            views.setOnClickFillInIntent(R.id.widget_item_text, fillInIntent);

            System.out.println("I ADD A VIEW"); */
            SystemClock.sleep(500);
            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
