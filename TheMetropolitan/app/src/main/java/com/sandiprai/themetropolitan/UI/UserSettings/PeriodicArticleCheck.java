package com.sandiprai.themetropolitan.UI.UserSettings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.JobIntentService;
import androidx.core.app.NotificationCompat;

import com.sandiprai.themetropolitan.TestWordPress;


public class PeriodicArticleCheck extends JobIntentService {
    //*
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String TAG = "PeriodicArticleCheck";
    public static final String ACTION_FOO = "com.sandiprai.themetropolitan.action.FOO";
    public static final String ACTION_BAZ = "com.sandiprai.themetropolitan.action.BAZ";
    public static final int NOTIFICATION_ID = 5567;
    SharedPreferences sharedpreferences;
    //SharedPreferences.Editor editor;
    NotificationsHelper helper;
    TestWordPress testWordPress;

    public static final int JOB_ID = 1;
    //amounts of time in milliseconds
    public static final int ONE_MIN = 60*1000;
    public static final int ONE_WEEK = 604800*1000;
    public static final int TWO_WEEKS = 1209600*1000;

    public PeriodicArticleCheck() {
        super();
    }

    //starts service in from BootReciever that starts on startup
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, PeriodicArticleCheck.class, JOB_ID, work);
    }

    public void onCreate() {
        super.onCreate();
        sharedpreferences = PreferenceManager.getDefaultSharedPreferences(this.getBaseContext());
        //editor = sharedpreferences.edit();
        Log.d(TAG, "onCreate"); //log the string message
    }

    //main method called that actually runs the code
    @Override //onHandleWork is equivalent to onHandleIntent
    protected void onHandleWork(@NonNull Intent intent) {
        testWordPress = new TestWordPress();

        boolean newArticleFound = false;
        String newestIdDatabase = "";
        String newestIdWordpress;
        Log.d(TAG,"onHandleWork"); //log the string that we are in this function

        //make a notification to display later
        helper = new NotificationsHelper(this);
        String input = "New Metropolitan articles found";
        String textTitle = "New Articles Found!";
        String textContent = "New articles were published. Press this to check them out.";
        NotificationCompat.Builder builder = helper.getChannelNotification(textTitle, textContent);



        for (int i = 0; i < 10; i++) {
            SystemClock.sleep(ONE_MIN); //in milliseconds 1209600000ms = 2 weeks
            Log.d(TAG, input + " - " + i); //log the string message

            //database call for latest entry id
            newestIdDatabase = sharedpreferences.getString("newestFirebaseID","0"); //insert Firebase method call here

            // article class call for WordPress latest article info to return id using
            // http://themetropolitan.metrostate.edu/wp-json/wp/v2/posts/?orderby=date&per_page=1&page=1
            newestIdWordpress = testWordPress.wpGetArticleIDByAge(1);

            if(newestIdDatabase != newestIdWordpress) {
                helper.getManager().notify(NOTIFICATION_ID, builder.build());
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy"); //log the string message
    }

    @Override
    public boolean onStopCurrentWork(){
        Log.d(TAG,"onStopCurrentWork"); //log the string message
        return super.onStopCurrentWork();
    }

}
