package com.faanggang.wisetrack;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class PublishExperimentFragment extends DialogFragment {

    private EditText inputName;
    private EditText inputDescription;
    private EditText inputRegion;
    private EditText inputMinTrials;
    private OnFragmentInteractionListener listener;

    public interface OnFragmentInteractionListener {
        void onPublish(Experiment newExperiment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.publish_1, null);

        inputName = view.findViewById(R.id.name_input);
        inputDescription = view.findViewById(R.id.description_input);
        inputRegion = view.findViewById(R.id.region_input);
        inputMinTrials = view.findViewById(R.id.minTrials_input);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle("Publish New Experiment")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String name = inputName.getText().toString();
                        String description = inputDescription.getText().toString();
                        String region = inputRegion.getText().toString();
                        int minTrials = Integer.parseInt(inputMinTrials.getText().toString());

                        listener.onPublish(
                                new Experiment(name, description, region, minTrials)
                        );

                    }}).create();

    }
}
