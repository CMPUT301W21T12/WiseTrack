package com.faanggang.wisetrack.search;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.faanggang.wisetrack.R;

// adapted from https://developer.android.com/guide/topics/ui/layout/recyclerview#java
// which is licensed under Apache 2.0 (http://www.apache.org/licenses/LICENSE-2.0)


/**
 * Custom RecyclerView item (i.e. RecyclerView.ViewHolder) class with basic TextView getters.
 */
public class ExperimentItemView extends RecyclerView.ViewHolder {
    private TextView title_TextView;
    private TextView description_TextView;
    private TextView date_TextView;
    private TextView status_TextView;
    private TextView owner_TextView;


    public ExperimentItemView(@NonNull View itemView) {
        super(itemView);

        title_TextView = itemView.findViewById(R.id.experiment_row_item_title_TextView);
        description_TextView = itemView.findViewById(R.id.experiment_row_item_desc_TextView);
        date_TextView = itemView.findViewById(R.id.experiment_row_item_date_TextView);
        status_TextView = itemView.findViewById(R.id.experiment_row_item_status_TextView);
        owner_TextView = itemView.findViewById(R.id.experiment_row_item_owner_TextView);
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
}
