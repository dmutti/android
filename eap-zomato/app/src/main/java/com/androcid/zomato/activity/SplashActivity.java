package com.androcid.zomato.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.androcid.zomato.R;
import com.androcid.zomato.activity.login.StartLoginActivity;
import com.androcid.zomato.preference.SessionPreference;
import com.androcid.zomato.util.Unbinding;
import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    private static final String TAG = SplashActivity.class.getSimpleName();
    private static final long SPLASH_TIMEOUT = 2300;
    private Context context = SplashActivity.this;
    private Handler closeHandler;
    private RelativeLayout activity_splash;
    private ImageView bgImage;
    private Handler handler;
    private long startTime = new Date().getTime();
    private Runnable closeRun = new Runnable() {
        @Override
        public void run() {
            if (SessionPreference.isLoggedIn(context)) {
                startActivity(HomeActivity.getCallIntent(context));

            } else {
                startActivity(StartLoginActivity.getCallIntent(context));
            }
            finish();
            return;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        bgImage = (ImageView) findViewById(R.id.bgImage);
        activity_splash = (RelativeLayout) findViewById(R.id.main);
        startTime = new Date().getTime();
        closeHandler = new Handler();
        startRunnable();
        handler = new Handler();
    }

    private void startRunnable() {
        closeHandler.postDelayed(closeRun, SPLASH_TIMEOUT);
    }

    @Override
    public void onBackPressed() {
        try {
            closeHandler.removeCallbacks(closeRun);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Unbinding.unbindDrawables(findViewById(R.id.main));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
