package com.github.dmutti.fcm.library;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessaging;

public class FRegistrationBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = FRegistrationBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
            FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

        } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
            String message = intent.getStringExtra("message");
        }
        displayFirebaseRegId(context);
    }

    private void displayFirebaseRegId(Context context) {
        SharedPreferences pref = context.getSharedPreferences(Config.SHARED_PREF, 0);
        String regId = pref.getString("regId", null);

        Log.d(TAG, "Firebase reg id: " + regId);

        if (!TextUtils.isEmpty(regId)) {
            Log.d(TAG, "Firebase Reg Id: " + regId);
        } else {
            Log.w(TAG, "Firebase Reg Id is not received yet!");
        }
    }

    public void register(Context context) {
        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(context).registerReceiver(this, new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(context).registerReceiver(this, new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(context);
    }

    public void unregister(Context context) {
        LocalBroadcastManager.getInstance(context).unregisterReceiver(this);
    }
}
