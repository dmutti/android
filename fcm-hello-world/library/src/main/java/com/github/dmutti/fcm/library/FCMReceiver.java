package com.github.dmutti.fcm.library;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;

//https://www.androidhive.info/2012/10/android-push-notifications-using-google-cloud-messaging-gcm-php-and-mysql/
public class FCMReceiver extends FirebaseMessagingService {

    private static final String TAG = FCMReceiver.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage == null) {
            return;
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                handleDataMessage(remoteMessage.getData());

            } catch (Exception e) {
                Log.e(TAG, "Error receiving message", e);
            }
        }
    }

    private void handleNotification(String message) {
        Log.d(TAG, "Notification: [" + message + "]");
    }

    private void handleDataMessage(Map<String, String> data) {
        try {
            NotificationContent content = new NotificationContent(data.get("tp"));
            content.setLine1(data.get("l1"));
            content.setLine2(data.get("l2"));
            content.setLine3(data.get("l3"));
            content.setLine1Color(data.get("l1c"));
            content.setLine2Color(data.get("l2c"));
            content.setLine3Color(data.get("l3c"));
            content.setBackgroundColor(data.get("bg"));
            content.setBanner(data.get("bn"));
            content.setIcon(data.get("ic"));
            content.setDestination(data.get("url"));

            Intent resultIntent = new Intent(Intent.ACTION_VIEW);
            if (!TextUtils.isEmpty(content.getDestination())) {
                resultIntent.setData(Uri.parse(content.getDestination()));
            }

            new NotificationUtils(getApplicationContext()).showNotificationMessage(content, resultIntent);

        } catch (Exception e) {
            Log.e(TAG, "Error handling data message", e);
        }
    }
}
