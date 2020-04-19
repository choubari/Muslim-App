package com.choubapp.muslimapp;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static com.choubapp.muslimapp.NotifsChannels.CHANNEL_1_ID;

public class AlarmReceiver extends BroadcastReceiver {
    private Random rng = new Random();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            // Set AGAIN the alarm when device booted.
            Log.d("TAG", "onReceive: BOOT_COMPLETED");
            return;
        }
        Calendar today = Calendar.getInstance();
        int currHH = today.get(Calendar.HOUR_OF_DAY);
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
        ArrayList<String> NotifMessages = intent.getStringArrayListExtra("ArrayMessages");
        int random1 = rng.nextInt(NotifMessages.size());
        int random2 = rng.nextInt(NotifMessages.size());
        //AdkarSabah Notification
        String title1 =intent.getStringExtra("NotifTitle1");
        String message1 = NotifMessages.get(random1);
        int id1 =intent.getIntExtra("NotifId1",0);
        int hh1 = intent.getIntExtra("NotifHH1",6);
        int mm1 = intent.getIntExtra("NotifMM1",0);
        if (id1!=0 && currHH<= hh1) {
            createNotif(context, contentIntent, id1, title1, message1);
        }
        //AdkarMassaa Notification
        String title2 =intent.getStringExtra("NotifTitle2");
        String message2 =NotifMessages.get(random2);
        int id2 =intent.getIntExtra("NotifId2",0);
        int hh2 = intent.getIntExtra("NotifHH2",19);
        if (id2!=0 && currHH<= hh2) {
            createNotif(context, contentIntent, id2, title2, message2);
        }
    }

    public void createNotif(Context context,PendingIntent intent, int id, String ttl, String msg){
        //Notification sound = default sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = { 0, 100, 200, 300 };
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.azkar)
                .setContentTitle(ttl)
                .setContentText(msg)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle(ttl)
                        .setSummaryText("الورد اليومي"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSound(alarmSound)
                .setVibrate(pattern)
                .setColor(16753737)
                .setLights(16753737,300,1000)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, notification);
    }
}
