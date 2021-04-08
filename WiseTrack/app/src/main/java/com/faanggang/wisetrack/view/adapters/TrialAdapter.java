package com.faanggang.wisetrack.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.TrialFetchManager;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Custom RecyclerView.Adapter class for trials
 */
public class TrialAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Trial> trials;
    private Context context;
    private UserManager userManager;
    private TrialItemView.OnTrialItemClickListener OnTrialItemClickListener;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public TrialAdapter(ArrayList<Trial> trials, Context context, TrialItemView.OnTrialItemClickListener onTrialItemClickListener) {
        this.trials = trials;
        this.context = context;
        this.userManager = new UserManager(FirebaseFirestore.getInstance());
        this.OnTrialItemClickListener = onTrialItemClickListener;
    }

    @NonNull
    @Override
    public TrialItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item_trial,
                parent, false);
        return new TrialItemView(view, context, OnTrialItemClickListener);
    }

    public String getDateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(date);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        TrialItemView item = (TrialItemView) holder;

        userManager.getUserInfo(trials.get(position).getExperimenterID(), task->{
            item.getTrialConductorTextView().setText(task.getResult().getString("userName"));
        });

        int trialType = trials.get(position).getTrialType();
        double trialResult = trials.get(position).getTrialResult();
        String trialResultView = "TRIAL RESULT";  // default TextView

        if ((trialType == 0)||((trialType == 2))) {  // count or NNIC trials
            // casting trial result to its proper data type
            trialResultView = Integer.toString((int)trialResult);
        } else if (trialType == 1) {  // binomial trials
            if ((int)trialResult == 0) {
                trialResultView = "Failure";
            } else if ((int)trialResult == 1) {
                trialResultView = "Success";
            } else {
                trialResultView = "Invalid";
            }
        } else if (trialType == 3) {  // measurement trials
            trialResultView = Float.toString(Float.parseFloat(df.format((float)trialResult)));
        }
        item.getResultTextView().setText(trialResultView);
        item.getDateTextView().setText(getDateString(trials.get(position).getDatetime()));
    }

    @Override
    public int getItemCount() {
        return trials.size();
    }
}
