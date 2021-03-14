package com.faanggang.wisetrack.experiment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.faanggang.wisetrack.R;

public class ExperimentActionMenuFragment extends DialogFragment {
    private Button subscribe;
    private Button unpublish;
    private Button endExperiment;
    private Button experimentResults;
    private Button geolocations;
    private Button executeTrials;
    private Button comment;
    private Button trials;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.experiment_action_menu_fragment, null);
    }
}
