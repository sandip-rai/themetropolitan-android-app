package com.sandiprai.themetropolitan;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.util.Random;

import androidx.annotation.Nullable;

public class PingCheckArticles_service extends Service {

    Random rand = new Random();
    //random id between 1000 and 5000
    public int notificationID = rand.nextInt((5000-1000)+1)+1000;
    private NotificationManager nMngr;
    int NOTIFICATION = R.string.local_service_started;

    public class LocalBinder extends Binder {
        PingCheckArticles_service getService() {
            return PingCheckArticles_service.this;
        }
    }

    @Override
    public void onCreate() {
        nMngr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        nMngr.cancel(NOTIFICATION);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients.
    private final IBinder mBinder = new LocalBinder();


    private void showNotification() {
        CharSequence text = getText(R.string.local_service_started);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);

        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.logo2)
                .setTicker(text)
                .setWhen(System.currentTimeMillis())
                .setContentText(getText(R.string.local_service_label))
                .setContentIntent(contentIntent)
                .build();


        nMngr.notify(NOTIFICATION, notification);
    }


}
