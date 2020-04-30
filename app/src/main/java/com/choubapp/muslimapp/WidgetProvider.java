package com.choubapp.muslimapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

public class WidgetProvider  extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {

            Intent serviceIntent = new Intent(context, WidgetService.class);
            serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.example_widget);
            views.setRemoteAdapter(R.id.example_widget_stack_view, serviceIntent);
            views.setEmptyView(R.id.example_widget_stack_view, R.id.example_widget_empty_view);

            Bundle appWidgetOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
            resizeWidget(appWidgetOptions, views);

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

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
        Toast.makeText(context, "onDeleted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEnabled(Context context) {
        Toast.makeText(context, "onEnabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisabled(Context context) {
        Toast.makeText(context, "onDisabled", Toast.LENGTH_SHORT).show();
    }
}