package com.faanggang.wisetrack.view.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.view.experiment.ViewExperimentActivity;

// adapted from https://developer.android.com/guide/topics/ui/layout/recyclerview#java
// which is licensed under Apache 2.0 (http://www.apache.org/licenses/LICENSE-2.0)


/**
 * Custom RecyclerView item (i.e. RecyclerView.ViewHolder) class with basic TextView getters.
 */
public class ExperimentItemView extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView titleTextView;
    private TextView descriptionTextView;
    private TextView dateTextView;
    private TextView statusTextView;
    private TextView ownerTextView;
    private String ID;
    private Context context;
    private int secondaryColor;
    private int primaryColor;
    private Experiment experiment;

    public ExperimentItemView(@NonNull View itemView, Context context) {
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
        this.context = context;
        titleTextView = itemView.findViewById(R.id.experiment_row_item_title_TextView);
        descriptionTextView = itemView.findViewById(R.id.experiment_row_item_desc_TextView);
        dateTextView = itemView.findViewById(R.id.experiment_row_item_dateValue_TextView);
        statusTextView = itemView.findViewById(R.id.experiment_row_item_status_TextView);
        ownerTextView = itemView.findViewById(R.id.experiment_row_item_ownerValue_TextView);
        secondaryColor = ContextCompat.getColor(context, R.color.darker_primary_text);
        primaryColor = ContextCompat.getColor(context, R.color.black);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(context, ViewExperimentActivity.class);
        intent.putExtra("EXP_ID",this.ID);
        intent.putExtra("EXP_OBJ", experiment);
        context.startActivity(intent);
    }

    public void setID(String id){
        this.ID = id;
    }

    public TextView getDateTextView() {
        return dateTextView;
    }

    public TextView getDescriptionTextView() {
        return descriptionTextView;
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public TextView getStatusTextView() {
        return statusTextView;
    }

    public TextView getOwnerTextView() {
        return ownerTextView;
    }

    /**
     * This method sets the color of the experiment title based on whether the experiment is
     * open or closed.
     * @param open
     * open is a boolean representing whether the experiment is open or not
     */
    public void setStatusColor(boolean open) {
        if (open) {
            titleTextView.setTextColor(primaryColor);
        } else {
            titleTextView.setTextColor(secondaryColor);
        }
    }

    public void setExperiment(Experiment e) {
        this.experiment = e;
    }
}
