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

import static com.choubapp.muslimapp.NotifsChannels.CHANNEL_1_ID;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            // Set AGAIN the alarm when device booted.
            Log.d("TAG", "onReceive: BOOT_COMPLETED");
            return;
        }
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
        //AdkarSabah Notification
        String title1 =intent.getStringExtra("NotifTitle1");
        String message1 =intent.getStringExtra("NotifMsg1");
        int id1 =intent.getIntExtra("NotifId1",0);
        if (id1!=0) createNotif(context, contentIntent, id1, title1, message1);
        //AdkarMassaa Notification
        String title2 =intent.getStringExtra("NotifTitle2");
        String message2 =intent.getStringExtra("NotifMsg2");
        int id2 =intent.getIntExtra("NotifId2",0);
        if (id2!=0) createNotif(context, contentIntent, id2, title2, message2);
    }

    public void createNotif(Context context,PendingIntent intent, int id, String ttl, String msg){
        //Notification sound = default sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = { 0, 100, 200, 300 };
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.azkar)
                .setContentTitle(ttl)
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setSound(alarmSound)
                .setVibrate(pattern)
                .setContentIntent(intent)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(id, notification);
    }
}
