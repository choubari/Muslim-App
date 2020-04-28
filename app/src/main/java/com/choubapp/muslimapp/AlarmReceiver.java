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
        System.out.println("Alarm Receiver");
        Calendar today = Calendar.getInstance();
        int hh =today.get(Calendar.HOUR_OF_DAY);
        int mm=today.get(Calendar.MINUTE);
        Intent activityIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, rng.nextInt(1000000), activityIntent, 0);
        ArrayList<String> NotifMessages = intent.getStringArrayListExtra("ArrayMessages");
        ArrayList<String> NotifSalatMessages = intent.getStringArrayListExtra("ArraySalatMessages");
        int random1 = rng.nextInt(NotifMessages.size());
        //AdkarSabah Notification
        String title10 =intent.getStringExtra("NotifTitle10");
        String message10 = NotifMessages.get(random1);
        int id10 =intent.getIntExtra("NotifId10",0);
        int hh10 = intent.getIntExtra("NotifTimeHH10",0);
        int mm10 = intent.getIntExtra("NotifTimeMM10",0);
        if (id10!=0 && hh<=hh10 && mm<=mm10) {
            createNotif(context, contentIntent, id10, title10, message10,"الورد اليومي");
        }
        random1 = rng.nextInt(NotifMessages.size());
        //AdkarMassaa Notification
        String title11 =intent.getStringExtra("NotifTitle11");
        String message11 =NotifMessages.get(random1);
        int id11 =intent.getIntExtra("NotifId11",0);
        int hh11 = intent.getIntExtra("NotifTimeHH11",0);
        int mm11 = intent.getIntExtra("NotifTimeMM11",0);
        if (id11!=0 && hh<=hh11 && mm<=mm11) {
            createNotif(context, contentIntent, id11, title11, message11,"الورد اليومي");
        }
        int random2 = rng.nextInt(NotifSalatMessages.size());
        String message1 = NotifSalatMessages.get(random2);
        String title1 =intent.getStringExtra("NotifTitle1");
        int id1 =intent.getIntExtra("NotifId1",0);
        int hh1 = intent.getIntExtra("NotifTimeHH1",0);
        int mm1 = intent.getIntExtra("NotifTimeMM1",0);
        if (id1!=0 && hh<=hh1 && mm<=mm1 ) {
            createNotif(context, contentIntent, id1, title1, message1,"أوقات الصلاة");
        }
        random2 = rng.nextInt(NotifSalatMessages.size());
        String message2 = NotifSalatMessages.get(random2);
        String title2 =intent.getStringExtra("NotifTitle2");
        int id2 =intent.getIntExtra("NotifId2",0);
        int hh2 = intent.getIntExtra("NotifTimeHH2",0);
        int mm2 = intent.getIntExtra("NotifTimeMM2",0);
        if (id2!=0 && hh<=hh2 && mm<=mm2) {
            createNotif(context, contentIntent, id1, title2, message2,"أوقات الصلاة");
        }
        random2 = rng.nextInt(NotifSalatMessages.size());
        String message3 = NotifSalatMessages.get(random2);
        String title3 =intent.getStringExtra("NotifTitle3");
        int id3 =intent.getIntExtra("NotifId3",0);
        int hh3 = intent.getIntExtra("NotifTimeHH3",0);
        int mm3 = intent.getIntExtra("NotifTimeMM3",0);
        if (id3!=0 && hh<=hh3 && mm<=mm3) {
            createNotif(context, contentIntent, id3, title3, message3,"أوقات الصلاة");
        }
        random2 = rng.nextInt(NotifSalatMessages.size());
        String message4 = NotifSalatMessages.get(random2);
        String title4 =intent.getStringExtra("NotifTitle4");
        int id4 =intent.getIntExtra("NotifId4",0);
        int hh4 = intent.getIntExtra("NotifTimeHH4",0);
        int mm4 = intent.getIntExtra("NotifTimeMM4",0);
        if (id4!=0 && hh<=hh4 && mm<=mm4) {
            createNotif(context, contentIntent, id4, title4, message4,"أوقات الصلاة");
        }
        random2 = rng.nextInt(NotifSalatMessages.size());
        String message5 = NotifSalatMessages.get(random2);
        String title5 =intent.getStringExtra("NotifTitle5");
        int id5 =intent.getIntExtra("NotifId5",0);
        int hh5 = intent.getIntExtra("NotifTimeHH5",0);
        int mm5 = intent.getIntExtra("NotifTimeMM5",0);
        if (id5!=0 && hh<=hh5 && mm<=mm5 ) {
            createNotif(context, contentIntent, id5, title5, message5, "أوقات الصلاة");
            System.out.println("id5 "+id5);
            System.out.println(" "+ hh +" "+hh5);
            System.out.println(" "+ mm + " "+mm5);
        }
    }

    public void createNotif(Context context,PendingIntent intent, int id, String ttl, String msg, String summary){
        //Notification sound = default sound
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = { 0, 100, 200, 300 };
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.iconwhitebg)
                .setContentTitle(ttl)
                .setContentText(msg)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .setBigContentTitle(ttl)
                        .setSummaryText(summary))
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
