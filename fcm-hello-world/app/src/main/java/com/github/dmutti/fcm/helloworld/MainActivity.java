package com.github.dmutti.fcm.helloworld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.github.dmutti.fcm.library.FRegistrationBroadcastReceiver;

//https://www.androidhive.info/2012/10/android-push-notifications-using-google-cloud-messaging-gcm-php-and-mysql/
//https://android.jlelse.eu/custom-layouts-for-your-push-notification-d8219d9962e
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FRegistrationBroadcastReceiver mRegistrationBroadcastReceiver;
    private TextView txtRegId, txtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtRegId = (TextView) findViewById(R.id.txt_reg_id);
        txtMessage = (TextView) findViewById(R.id.txt_push_message);

        mRegistrationBroadcastReceiver = new FRegistrationBroadcastReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mRegistrationBroadcastReceiver.register(this);
        } catch (Exception e) {
            Log.e(TAG, "Error registering broadcast receiver", e);
        }
    }

    @Override
    protected void onPause() {
        try {
            mRegistrationBroadcastReceiver.unregister(this);
        } catch (Exception e) {
            Log.e(TAG, "Error deregistering broadcast receiver", e);
        }
        super.onPause();
    }
}
