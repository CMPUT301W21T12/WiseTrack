package com.faanggang.wisetrack.experiment;

import android.content.Context;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ExperimentActionMenuFragment extends DialogFragment {
    private TextView subscribe;
    private TextView unpublish;
    private TextView endExperiment;
    private TextView experimentResults;
    private TextView geolocations;
    private TextView executeTrials;
    private TextView comment;
    private TextView trials;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
