package com.sandiprai.themetropolitan.UI.UserSettings;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.sandiprai.themetropolitan.R;
import com.sandiprai.themetropolitan.UI.MainUI.MainActivity;


public class NotificationsHelper extends ContextWrapper {

    private static final String NOTIFIY_CHANNEL_ID = "com.sandiprai.themetropolitan.UI.UserSettings.NOTIFICATIONS";
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

}
