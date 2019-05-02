package com.sandiprai.themetropolitan.UI.UserSettings;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class BootReceiver extends BroadcastReceiver {

    @Override //Context context, Intent intent
    public void onReceive(Context context, Intent intent) {
        final String TAG = "onDeviceBoot";
        Log.d(TAG, "onReceive of the BootReceiver.java class - - - - - - - - - - - -  - - -------------------    @@@    ---------------------");

        //on boot start the PeriodicArticleCheck service
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d(TAG, "onReceive of the BootReceiver.java class - scheduling The Metropolitan new article check service");
            PeriodicArticleCheck.enqueueWork(context,new Intent());
        }
    }
}
