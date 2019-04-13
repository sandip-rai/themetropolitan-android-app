package com.sandiprai.themetropolitan;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;

import java.util.Random;

import androidx.core.app.NotificationCompat;


public class Notifications extends Activity {

    MainActivity mainActivity = new MainActivity();
    public static String CHANNEL_ID = "new_articles";
    String title = getString(R.string.app_name);
    NotificationCompat.Builder builder;
    Random rand = new Random();

    public int notificationID;
    Editor editor = mainActivity.sharedPreferences.edit();
    Intent intent = new Intent(this, MainActivity.class);

    public Notifications(){
        //random id between 1000 and 5000
        this.notificationID = rand.nextInt((5000-1000)+1)+1000;
    }


    public void makeNotification() {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(title)
                .setContentText("The Metropolitan has published new articles")
                //.setContentIntent(pendingIntent)
                //.setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationManagerCompat mNotificationManager =
//                NotificationManagerCompat.from(this);
        //saveID();
        mNotificationManager.notify(notificationID, builder.build());
    }

    private void saveID() {
        // mId allows you to update the notification later on, this is a unique identifier within this app for the notification.
        String mId = "notifyMNA";
        editor.putInt(mId, notificationID);
        editor.commit();
    }
}
