package com.faanggang.wisetrack.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.experiment.Experiment;

import java.util.ArrayList;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.experiment.ExperimentManager;
import com.faanggang.wisetrack.user.UserManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

// adapted from https://developer.android.com/guide/topics/ui/layout/recyclerview#java
// which is licensed under Apache 2.0 (http://www.apache.org/licenses/LICENSE-2.0)


/**
 * Custom RecyclerView.Adapter class
 */
public class ExperimentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Experiment> experiments;
    private Context context;
    private UserManager userManager;

    public ExperimentAdapter(Context context, ArrayList<Experiment> experiments) {
        this.experiments = experiments;
        this.context = context;
        this.userManager = new UserManager(FirebaseFirestore.getInstance());
    }
    @NonNull
    @Override
    public ExperimentItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_experiment,
            parent, false);
        return new ExperimentItemView(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ExperimentItemView item = (ExperimentItemView) holder;
        item.setID(experiments.get(position).getExpID());
        item.getTitle_TextView().setText(experiments.get(position).getName());
        userManager.getUserInfo(experiments.get(position).getOwnerID(), task->{
            item.getOwner_TextView().setText(task.getResult().getString("userName"));
        });

        item.getDescription_TextView().setText(experiments.get(position).getDescription());
        if (experiments.get(position).isOpen()) {
            item.getStatus_TextView().setText(R.string.search_item_Open);
            item.setStatusColor(true);
        } else {
            item.getStatus_TextView().setText(R.string.search_item_Closed);
            item.setStatusColor(false);
        }
    }

    @Override
    public int getItemCount() {
        return experiments.size();
    }


}
