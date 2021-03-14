package com.faanggang.wisetrack.experiment;

import android.app.AlertDialog;
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
    private Button subscribeButton;
    private Button unpublishButton;
    private Button endButton;
    private Button viewResultsButton;
    private Button viewGeolocationsButton;
    private Button executeTrialsButton;
    private Button commentButton;
    private Button viewTrialsButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.experiment_action_menu_fragment, null);
        subscribeButton = view.findViewById(R.id.subscribe_button);
        unpublishButton = view.findViewById(R.id.unpublish_button);
        endButton = view.findViewById(R.id.end_experiment_button);
        viewResultsButton = view.findViewById(R.id.results_button);
        viewGeolocationsButton = view.findViewById(R.id.geolocations_button);
        executeTrialsButton = view.findViewById(R.id.execute_trials_button);
        commentButton = view.findViewById(R.id.comment_button);
        viewTrialsButton = view.findViewById(R.id.trials_button);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view).create();
    }

}
