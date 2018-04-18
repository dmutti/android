package com.github.dmutti.fcm.library;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context context;

    public NotificationUtils(Context context) {
        this.context = context;
    }

    public void showNotificationMessage(String title, String message, Intent intent) {
        showNotificationMessage(title, message, intent, null, null);
    }

    public void showNotificationMessage(final String title, final String message, Intent intent, String iconUrl, String imageUrl) {
        // Check for empty push message
        if (TextUtils.isEmpty(message)) {
            return;
        }

        // notification icon
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, Config.DEFAULT_CHANNEL_ID);

        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/notification");

        if (!TextUtils.isEmpty(imageUrl) && !TextUtils.isEmpty(iconUrl)) {
            Bitmap banner = getBitmapFromURL(imageUrl);
            Bitmap icon = getBitmapFromURL(iconUrl);
            showBigCustomNotification(banner, mBuilder, icon, title, message, resultPendingIntent, alarmSound);

        } else {
            showSmallNotification(mBuilder, R.drawable.ic_stat_name, title, message, resultPendingIntent, alarmSound);
            playNotificationSound();
        }
    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
        style.bigText(message);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(style)
                .setSmallIcon(R.drawable.ic_stat_name)
                //.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), icon))
                .setLargeIcon(null)
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }

    private void showBigCustomNotification(Bitmap banner, NotificationCompat.Builder mBuilder, Bitmap icon, String title, String message, PendingIntent resultPendingIntent, Uri alarmSound) {
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_push);
        contentView.setImageViewBitmap(R.id.icon, icon);
        contentView.setTextViewText(R.id.line1, title);
        contentView.setTextViewText(R.id.line2, message);
        contentView.setImageViewBitmap(R.id.bigPicture, banner);

        Notification notification = mBuilder
                .setSmallIcon(R.drawable.ic_stat_name)
                .setCustomBigContentView(contentView)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    public Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + context.getPackageName() + "/raw/notification");
            RingtoneManager.getRingtone(context, alarmSound).play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method checks if the app is in background or not
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
