package com.faanggang.wisetrack.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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
public class TrialItemView extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final TextView resultTextView;
    private final TextView trialConductorTextView;
    private final TextView dateTextView;
    private final OnTrialItemClickListener onTrialItemClickListener;

    public TrialItemView(@NonNull View itemView, Context context, OnTrialItemClickListener onTrialItemClickListener) {
        super(itemView);
        this.onTrialItemClickListener = onTrialItemClickListener;
        itemView.setClickable(true);
        itemView.setOnClickListener(this);

        resultTextView = itemView.findViewById(R.id.textview_trial_row_item_resultValue);
        trialConductorTextView = itemView.findViewById(R.id.textview_trial_row_item_usernameValue);
        dateTextView = itemView.findViewById(R.id.textview_trial_row_item_dateValue);
    }

    @Override
    public void onClick(View v) {
        onTrialItemClickListener.onItemClick(getAdapterPosition(), v);
    }

    public interface OnTrialItemClickListener {
        void onItemClick(int position, View view);
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
