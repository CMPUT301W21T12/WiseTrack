package com.faanggang.wisetrack.view.trial;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.SubscriptionManager;
import com.faanggang.wisetrack.controllers.TrialFetchManager;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.view.adapters.TrialAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import android.os.Bundle;

import java.util.ArrayList;

public class ViewTrialsActivity extends AppCompatActivity implements TrialFetchManager.TrialFetcher {
    private RecyclerView recyclerView;
    private TrialAdapter trialAdapter;
    private TrialFetchManager trialFetchManager;
    private ArrayList<Trial> trials;
    private String expID;  // experiment ID of the current experiment whose trials are fetched

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trials);

        trialFetchManager = new TrialFetchManager(this);
        trials = new ArrayList<>();
        trialAdapter = new TrialAdapter(trials, this);

        recyclerView = findViewById(R.id.recyclerview_view_trials);
        recyclerView.setAdapter(trialAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        Bundle extras = getIntent().getExtras();
        expID = extras.getString("EXP_ID");

        trialFetchManager.fetchTrials(expID);
    }

    /**
     * This method makes the RecyclerView display trials of current experiment
     * @param results
     *     results are trials to display which were successfully fetched from Cloud Firestore.
     */
    @Override
    public void onSuccessfulFetch(ArrayList<Trial> results) {
        trials.clear();
        trials.addAll(results);
        trialAdapter.notifyDataSetChanged();
    }
}