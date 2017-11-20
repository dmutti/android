package com.hfad.workout;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.Locale;

public class StopwatchFragment extends Fragment implements View.OnClickListener {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    private final String TAG = StopwatchFragment.class.getSimpleName();

    public StopwatchFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds", 0);
            running = savedInstanceState.getBoolean("running", false);
            wasRunning = savedInstanceState.getBoolean("wasRunning", false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("seconds", seconds);
        outState.putBoolean("running", running);
        outState.putBoolean("wasRunning", wasRunning);
    }

    /*
    The onPause() method runs when the activity stops being in the foreground.
    The next activity isn’t resumed until this method finishes, so any code in this method needs to be quick.
    */
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        wasRunning = running;
        running = false;
    }

    /*
    If the activity moves into the foreground again, the onResume() method gets called.
     */
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        runTimer(view);

        view.findViewById(R.id.start_button).setOnClickListener(this);
        view.findViewById(R.id.stop_button).setOnClickListener(this);
        view.findViewById(R.id.reset_button).setOnClickListener(this);
        return view;
    }

    private void runTimer(View view) {
        final TextView timeView = view.findViewById(R.id.time_view);
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

    private void onClickStart() {
        running = true;
    }

    private void onClickStop() {
        running = false;
    }

    private void onClickReset() {
        running = false;
        seconds = 0;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_button : onClickStart(); break;
            case R.id.stop_button: onClickStop(); break;
            case R.id.reset_button: onClickReset(); break;
        }
    }
}
