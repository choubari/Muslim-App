package com.choubapp.muslimapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterViewFlipper;
import android.widget.Button;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider  extends AppWidgetProvider {
    Button next;
    public static final String PREVIOUS_ACTION = "com.choubapp.muslimapp.PREVIOUS";
    public static final String NEXT_ACTION = "com.choubapp.muslimapp.NEXT";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            Intent serviceIntent = new Intent(context, WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
            views.setRemoteAdapter(R.id.adapterViewFlipper, serviceIntent);
            views.setEmptyView(R.id.adapterViewFlipper, R.id.example_widget_empty_view);
            /*final Intent nextIntent = new Intent(context, WidgetProvider.class);
            nextIntent.setAction(WidgetProvider.NEXT_ACTION);
            nextIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            final PendingIntent nextPendingIntent = PendingIntent
                    .getBroadcast(context, 0, nextIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.NEXT, nextPendingIntent); */
            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            resizeWidget(appWidgetOptions, views);
            /*final Intent previousIntent = new Intent(context, WidgetProvider.class);
            previousIntent.setAction(WidgetProvider.PREVIOUS_ACTION);
            previousIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            final PendingIntent previousPendingIntent = PendingIntent
                    .getBroadcast(context, 1, previousIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.PREVIOUS, previousPendingIntent);*/
            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
       // super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

  /*  @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        System.out.println("I ON RECEIVE");

        if (action.equals(PREVIOUS_ACTION)) {
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context,
                    WidgetProvider.class);
            mgr.notifyAppWidgetViewDataChanged(mgr.getAppWidgetIds(cn),
                    R.id.adapterViewFlipper);
        }
        if (action.equals(NEXT_ACTION)) {
            RemoteViews rv = new RemoteViews(context.getPackageName(),
                    R.layout.example_widget);

            rv.showNext(R.id.adapterViewFlipper);

            AppWidgetManager.getInstance(context).partiallyUpdateAppWidget(
                    intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                            AppWidgetManager.INVALID_APPWIDGET_ID), rv);
        }

        super.onReceive(context, intent);
    }*/


    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);

        resizeWidget(newOptions, views);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private void resizeWidget(Bundle appWidgetOptions, RemoteViews views) {
        int minWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = appWidgetOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);

        if (maxHeight > 100) {
            views.setViewVisibility(R.id.example_widget_text, View.VISIBLE);
        } else {
            views.setViewVisibility(R.id.example_widget_text, View.GONE);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}