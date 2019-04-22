package com.sandiprai.themetropolitan;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;


public class NotificationsHelper extends ContextWrapper {

    private static final String NOTIFIY_CHANNEL_ID = "com.sandiprai.themetropolitan.NOTIFICATIONS";
    private static final String NOTIFY_CHANNEL_NAME = "New Articles";
    private NotificationManager manager;
    public Intent intent = new Intent(this, MainActivity.class);
    public PendingIntent actionPendingIntent;
    NotificationCompat.Builder builder;


    public NotificationsHelper(Context base) {
        super(base);
        createChannels();
    }


    private void createChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFIY_CHANNEL_ID,NOTIFY_CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.GREEN);
            //notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(notificationChannel);
        }
    }


    public NotificationManager getManager() {
        if(manager == null) {
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }


    public NotificationCompat.Builder getChannelNotification(String title, String body) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        actionPendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        builder = new NotificationCompat.Builder(getApplicationContext(),NOTIFIY_CHANNEL_ID)
                .setSmallIcon(R.drawable.logo2)
                .setContentText(body)
                .setContentTitle(title)
                .setAutoCancel(true);

        builder.setContentIntent(actionPendingIntent);

        return builder;
//        } else {
//            return ;
//        }
    }


    /*
    public static final String CHANNEL_ID = "new_articles";
    //String title = getString(R.string.app_name);
    //public NotificationCompat.Builder builder;
    //Random rand = new Random();
    public Intent intent;// = new Intent(this, MainActivity.class);
    public int notificationID;
    public PendingIntent actionPendingIntent;// = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT);


    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public Notifications(){
        //random id between 1000 and 5000
        //setNotificationID(rand.nextInt((5000-1000)+1)+1000);
        //MainActivity mainActivity = new MainActivity();
        //Editor editor = mainActivity.sharedPreferences.edit();
//        setNotificationID(1);
//        this.intent = new Intent(this, MainActivity.class);
//        this.actionPendingIntent = PendingIntent.getActivity(this,0,this.intent, PendingIntent.FLAG_UPDATE_CURRENT);


    }


    public void makeNotification(View view) {

        this.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo2)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("The Metropolitan has published new articles")
                //.setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        builder.setContentIntent(this.actionPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationManagerCompat mNotificationManager =
//                NotificationManagerCompat.from(this);
//        saveID();
        mNotificationManager.notify(this.notificationID, builder.build());
    }

    private void saveID() {
        // mId allows you to update the notification later on, this is a unique identifier within this app for the notification.
        String mId = "notifyMNA";
        //editor.putInt(mId, notificationID);
        //editor.commit();
    }
    //*/
}
