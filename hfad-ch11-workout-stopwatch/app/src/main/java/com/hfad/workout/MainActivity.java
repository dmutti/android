package com.hfad.workout;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void itemClicked(long id) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {//running on a large screen
            WorkoutDetailFragment details = new WorkoutDetailFragment();
            details.setWorkoutId(id);

            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.fragment_container, details);
            tx.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            tx.addToBackStack(null);
            tx.commit();

        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.EXTRA_WORKOUT_ID, (int) id);
            startActivity(intent);
        }
    }
}
