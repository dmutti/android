package com.androcid.zomato.activity.login;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.androcid.zomato.R;

public class StartLoginActivity extends AppCompatActivity {

    private static final String TAG = StartLoginActivity.class.getSimpleName();
    private Context context = StartLoginActivity.this;

    public static Intent getCallIntent(Context context) {
        Intent intent = new Intent(context, StartLoginActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_login);
    }
}
