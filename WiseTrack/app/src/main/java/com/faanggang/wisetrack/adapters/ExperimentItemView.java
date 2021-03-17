package com.faanggang.wisetrack.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.experiment.ViewExperimentActivity;

// adapted from https://developer.android.com/guide/topics/ui/layout/recyclerview#java
// which is licensed under Apache 2.0 (http://www.apache.org/licenses/LICENSE-2.0)


/**
 * Custom RecyclerView item (i.e. RecyclerView.ViewHolder) class with basic TextView getters.
 */
public class ExperimentItemView extends RecyclerView.ViewHolder implements View.OnClickListener{
    private TextView title_TextView;
    private TextView description_TextView;
    private TextView date_TextView;
    private TextView status_TextView;
    private TextView owner_TextView;
    private String ID;
    private Context context;
    private int secondaryColor;
    private int primaryColor;


    public ExperimentItemView(@NonNull View itemView, Context context) {
        super(itemView);
        itemView.setClickable(true);
        itemView.setOnClickListener(this);
        this.context = context;
        title_TextView = itemView.findViewById(R.id.experiment_row_item_title_TextView);
        description_TextView = itemView.findViewById(R.id.experiment_row_item_desc_TextView);
        date_TextView = itemView.findViewById(R.id.experiment_row_item_dateValue_TextView);
        status_TextView = itemView.findViewById(R.id.experiment_row_item_status_TextView);
        owner_TextView = itemView.findViewById(R.id.experiment_row_item_ownerValue_TextView);
        secondaryColor = ContextCompat.getColor(context, R.color.darker_primary_text);
        primaryColor = ContextCompat.getColor(context, R.color.black);
    }

    @Override
    public void onClick(View v){
        Intent intent = new Intent(context, ViewExperimentActivity.class);
        intent.putExtra("EXP_ID",this.ID);
        context.startActivity(intent);
    }

    public void setID(String id){
        this.ID = id;
    }

    public TextView getDate_TextView() {
        return date_TextView;
    }

    public TextView getDescription_TextView() {
        return description_TextView;
    }

    public TextView getTitle_TextView() {
        return title_TextView;
    }

    public TextView getStatus_TextView() {
        return status_TextView;
    }

    public TextView getOwner_TextView() {
        return owner_TextView;
    }

    public void setStatusColor(boolean open) {
        if (open) {
            title_TextView.setTextColor(primaryColor);
        } else {
            title_TextView.setTextColor(secondaryColor);
        }
    }
}
