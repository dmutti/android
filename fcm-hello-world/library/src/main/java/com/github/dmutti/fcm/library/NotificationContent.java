package com.github.dmutti.fcm.library;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class NotificationContent {

    private static final String TAG = NotificationContent.class.getSimpleName();

    private final String type;
    private String line1;
    private String line1Color = "#000000";
    private String line2;
    private String line2Color = "#000000";
    private String line3;
    private String line3Color = "#000000";
    private String backgroundColor;
    private Bitmap bitmapIcon;
    private String icon;
    private Bitmap bitmapBanner;
    private String banner;
    private String destination;

    public NotificationContent(String type) {
        this.type = type;
    }

    public String getLine1() {
        return line1;
    }
    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine1Color() {
        return line1Color;
    }
    public void setLine1Color(String line1Color) {
        if (!TextUtils.isEmpty(line1Color)) {
            this.line1Color = line1Color;
        }
    }

    public String getLine2() {
        return line2;
    }
    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getLine2Color() {
        return line2Color;
    }
    public void setLine2Color(String line2Color) {
        if (!TextUtils.isEmpty(line2Color)) {
            this.line2Color = line2Color;
        }
    }

    public String getLine3() {
        return line3;
    }
    public void setLine3(String line3) {
        this.line3 = line3;
    }

    public String getLine3Color() {
        return line3Color;
    }
    public void setLine3Color(String line3Color) {
        if (!TextUtils.isEmpty(line3Color)) {
            this.line3Color = line3Color;
        }
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }
    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Bitmap getBitmapIcon() {
        if (bitmapIcon == null) {
            bitmapIcon = getBitmapFromURL(icon);
        }
        return bitmapIcon;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Bitmap getBitmapBanner() {
        if (bitmapBanner == null) {
            bitmapBanner = getBitmapFromURL(banner);
        }
        return bitmapBanner;
    }
    public String getBanner() {
        return banner;
    }
    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getType() {
        return type;
    }

    public String getDestination() {
        return destination;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }

    private Bitmap getBitmapFromURL(String arg) {
        if (TextUtils.isEmpty(arg)) {
            return null;
        }

        try {
            URL url = new URL(arg);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);

        } catch (Exception e) {
            Log.e(TAG, "Error downloading image [" + arg + "]", e);
        }
        return null;
    }
}
