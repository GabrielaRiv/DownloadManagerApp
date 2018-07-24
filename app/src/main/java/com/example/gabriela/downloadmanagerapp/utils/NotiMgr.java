package com.example.gabriela.downloadmanagerapp.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.example.gabriela.downloadmanagerapp.MainActivity;
import com.example.gabriela.downloadmanagerapp.R;

public class NotiMgr {

    private Context context;
    private static final String CHANNEL_ID = "10";

    public NotiMgr(Context context) {
        this.context = context;
    }

    // Create an explicit intent for an Activity in the app
    Intent intent = new Intent(context, MainActivity.class);
    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

    public NotificationCompat.Builder notificationfuntion(String title, String description){
        // Set the notification content
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_cloud_download)
                .setContentTitle(title)
                .setContentText(description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        return mBuilder;
    }

   public void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

   public void showNotification(){
       NotiMgr notiMgr = new NotiMgr(context);
       notiMgr.createNotificationChannel();
       NotificationCompat.Builder mBuilder = notiMgr.notificationfuntion("title", "description");

       Intent intent = new Intent(context, MainActivity.class);
       intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
       PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

       mBuilder.setContentIntent(pendingIntent);

       //Show the notification
       NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
       // notificationId is a unique int for each notification that you must define
       notificationManager.notify(10, mBuilder.build());
   }
}
