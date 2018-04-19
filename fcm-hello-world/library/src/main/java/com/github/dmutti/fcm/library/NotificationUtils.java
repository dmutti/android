package com.github.dmutti.fcm.library;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.widget.RemoteViews;

public class NotificationUtils {

    private static String TAG = NotificationUtils.class.getSimpleName();
    private Context context;

    public NotificationUtils(Context context) {
        this.context = context;
    }

    public void showNotificationMessage(NotificationContent content, Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Config.DEFAULT_CHANNEL_ID);

        if ("big_image_and_icon".equals(content.getType())) {
            showBigCustomNotification(builder, content, resultPendingIntent);

        } else if ("text_and_icon".equals(content.getType())) {
            showSmallNotification(builder, content, resultPendingIntent);
        }
    }

    private void showSmallNotification(NotificationCompat.Builder builder, NotificationContent content, PendingIntent resultPendingIntent) {
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_push_small);
        contentView.setImageViewBitmap(R.id.icon, content.getBitmapIcon());

        contentView.setTextViewText(R.id.line1, content.getLine1());
        contentView.setTextColor(R.id.line1, Color.parseColor(content.getLine1Color()));

        contentView.setTextViewText(R.id.line2, content.getLine2());
        contentView.setTextColor(R.id.line2, Color.parseColor(content.getLine2Color()));

        contentView.setTextViewText(R.id.line3, content.getLine3());
        contentView.setTextColor(R.id.line3, Color.parseColor(content.getLine3Color()));

        if (!TextUtils.isEmpty(content.getBackgroundColor())) {
            contentView.setInt(R.id.custom_push_small, "setBackgroundColor", Color.parseColor(content.getBackgroundColor()));
        }

        Notification notification = builder
                .setSmallIcon(R.drawable.ic_stat_name)
                .setCustomContentView(contentView)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setOnlyAlertOnce(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID, notification);
    }

    private void showBigCustomNotification(NotificationCompat.Builder builder, NotificationContent content, PendingIntent resultPendingIntent) {
        RemoteViews contentView = new RemoteViews(context.getPackageName(), R.layout.custom_push);
        contentView.setImageViewBitmap(R.id.icon, content.getBitmapIcon());
        contentView.setImageViewBitmap(R.id.bigPicture, content.getBitmapBanner());

        contentView.setTextViewText(R.id.line1, content.getLine1());
        contentView.setTextColor(R.id.line1, Color.parseColor(content.getLine1Color()));

        contentView.setTextViewText(R.id.line2, content.getLine2());
        contentView.setTextColor(R.id.line2, Color.parseColor(content.getLine2Color()));

        contentView.setTextViewText(R.id.line3, content.getLine3());
        contentView.setTextColor(R.id.line3, Color.parseColor(content.getLine3Color()));

        if (!TextUtils.isEmpty(content.getBackgroundColor())) {
            contentView.setInt(R.id.custom_push, "setBackgroundColor", Color.parseColor(content.getBackgroundColor()));
        }

        Notification notification = builder
                .setSmallIcon(R.drawable.ic_stat_name)
                .setCustomContentView(contentView)
                .setCustomBigContentView(contentView)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setOnlyAlertOnce(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Config.NOTIFICATION_ID_BIG_IMAGE, notification);
    }

    // Clears notification tray messages
    public static void clearNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
