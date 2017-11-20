package com.hfad.workout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class WorkoutDetailFragment extends Fragment {

    private long workoutId;

    public WorkoutDetailFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            workoutId = savedInstanceState.getLong("workoutId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_workout_detail, container, false);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putLong("workoutId", workoutId);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();
        if (view == null) {
            return;
        }
        Workout workout = Workout.workouts[(int) workoutId];

        TextView title = view.findViewById(R.id.text_title);
        title.setText(workout.getName());
        TextView desc = view.findViewById(R.id.text_description);
        desc.setText(workout.getDescription());
    }

    public void setWorkoutId(long workoutId) {
        this.workoutId = workoutId;
    }
}
