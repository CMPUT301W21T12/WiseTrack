package com.faanggang.wisetrack.view.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.TrialFetchManager;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

/**
 *
 */
public class TrialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Experiment> trials;
    private Context context;
    private TrialFetchManager trialFetchManager;
    private TrialFetchManager.TrialFetcher fetcher;

    public TrialAdapter(TrialFetchManager.TrialFetcher fetcher, ArrayList<Experiment> trials, Context context) {
        this.fetcher = fetcher;
        this.trials = trials;
        this.context = context;
        this.trialFetchManager = new TrialFetchManager(FirebaseFirestore.getInstance(), fetcher);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return trials.size();
    }
}
