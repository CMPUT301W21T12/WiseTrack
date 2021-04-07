package com.faanggang.wisetrack.view.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.view.experiment.ViewExperimentActivity;
import com.faanggang.wisetrack.view.trial.ViewTrialsActivity;

import org.w3c.dom.Text;

/**
 * Custom RecyclerView item (i.e. RecyclerView.ViewHolder) class with basic TextView getters for trials.
 */
public class TrialItemView extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView resultTextView;
    private TextView trialConductorTextView;
    private TextView dateTextView;
    private Context context;

    public TrialItemView(@NonNull View itemView, Context context) {
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
        this.context = context;

        resultTextView = itemView.findViewById(R.id.textview_trial_row_item_resultValue);
        trialConductorTextView = itemView.findViewById(R.id.textview_trial_row_item_usernameValue);
        dateTextView = itemView.findViewById(R.id.textview_trial_row_item_dateValue);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, ViewTrialsActivity.class);
        context.startActivity(intent);
    }

    public TextView getResultTextView() {
        return resultTextView;
    }

    public TextView getTrialConductorTextView() {
        return trialConductorTextView;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }
}
