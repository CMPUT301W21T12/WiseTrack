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
import com.faanggang.wisetrack.view.adapters.TrialItemView;
import com.faanggang.wisetrack.view.comment.AddCommentFragment;
import com.google.api.LogDescriptor;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import static androidx.camera.core.CameraXThreads.TAG;

public class ViewTrialsActivity extends AppCompatActivity implements TrialFetchManager.TrialFetcher, TrialItemView.OnTrialItemClickListener {
    private RecyclerView recyclerView;
    private TrialAdapter trialAdapter;
    private TrialFetchManager trialFetchManager;
    private ArrayList<Trial> trials;
    private String expID;  // experiment ID of the current experiment whose trials are fetched
    public static final String TAG = "CameraX-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_trials);

        trialFetchManager = new TrialFetchManager(this);
        trials = new ArrayList<>();
        trialAdapter = new TrialAdapter(trials, this, this);

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

    @Override
    public void onItemClick(int position) {
        Log.w("onItemClick: clicked.", String.valueOf(position));
        //Intent intent = new Intent(this, TrialItemOnClickFragment.class);
        //startActivity(intent);
    }
}