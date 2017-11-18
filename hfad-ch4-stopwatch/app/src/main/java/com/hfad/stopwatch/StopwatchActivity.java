package com.hfad.stopwatch;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;


//Pag 155
public class StopwatchActivity extends Activity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private final String TAG = StopwatchActivity.class.getSimpleName();

    /*
    The activity gets launched, and the onCreate() method runs
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds", 0);
            running = savedInstanceState.getBoolean("running", false);
            wasRunning = savedInstanceState.getBoolean("wasRunning", false);
        }
        runTimer();
    }

    private void runTimer() {
        final TextView timeView = findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format(Locale.getDefault(), "%d:%02d:%02d", hours, minutes, secs);

                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }

    public void onClickStart(View v) {
        running = true;
    }

    public void onClickStop(View v) {
        running = false;
    }

    public void onClickReset(View v) {
        running = false;
        seconds = 0;
    }

    /*
    The onStart() method runs. It gets called when the activity is about to become visible
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }


    /*
    The onStop() method runs when the activity stops being visible to the user
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    /*
    If the activity becomes visible to the user again, the onRestart() method gets called followed by onStart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    /*
    The onStop() method will get called before onDestroy()
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    /*
    The onPause() method runs when the activity stops being in the foreground.
    The next activity isn’t resumed until this method finishes, so any code in this method needs to be quick.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        wasRunning = running;
        running = false;
    }

    /*
    If the activity moves into the foreground again, the onResume() method gets called.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }
}
