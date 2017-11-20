package com.hfad.workout;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class TempActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);
        if (savedInstanceState != null) {
            return; //activity is being recreated after being destroyed
        }

        StopwatchFragment fragment = new StopwatchFragment();
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(R.id.stopwatch_container, fragment);
        tx.addToBackStack(null);
        tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        tx.commit();
    }
}
