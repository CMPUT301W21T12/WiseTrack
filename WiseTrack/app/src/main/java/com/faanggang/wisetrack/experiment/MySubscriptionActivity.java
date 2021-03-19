package com.faanggang.wisetrack.experiment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.WiseTrackApplication;
import com.faanggang.wisetrack.adapters.ExperimentAdapter;
import com.faanggang.wisetrack.comment.SubscriptionManager;

import java.util.ArrayList;

public class MySubscriptionActivity extends AppCompatActivity implements SubscriptionManager.subSearcher {
    private ExperimentAdapter expAdapter;
    private RecyclerView recyclerView;
    private SubscriptionManager subManager;
    private ArrayList<Experiment> experiments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_experiments);
        subManager = new SubscriptionManager(this);

        experiments = new ArrayList<Experiment>();

        expAdapter= new ExperimentAdapter(this, experiments);

        recyclerView = findViewById(R.id.my_experiments_recyclerview);
        recyclerView.setAdapter(expAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );

        subManager.getSubscriptions(WiseTrackApplication.getCurrentUser().getUserID());

    }
    @Override
    public void onSubscriptionsFound(ArrayList<Experiment> results) {
        experiments.clear();
        experiments.addAll(results);
        expAdapter.notifyDataSetChanged();
    }
}
