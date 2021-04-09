package com.faanggang.wisetrack.view.experiment;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.model.experiment.Searcher;
import com.faanggang.wisetrack.view.adapters.ExperimentAdapter;

import java.util.ArrayList;

public class MyExperimentActivity extends AppCompatActivity implements Searcher {
    private ExperimentAdapter expAdapter;
    private RecyclerView recyclerView;
    private ExperimentManager expManager;
    private ArrayList<Experiment> experiments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_experiments);
        expManager = new ExperimentManager(this);

        experiments = new ArrayList<Experiment>();

        expAdapter= new ExperimentAdapter(this, experiments);

        recyclerView = findViewById(R.id.my_experiments_recyclerview);
        recyclerView.setAdapter(expAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        expManager.userExpQuery(WiseTrackApplication.getCurrentUser().getUserID());

    }
    @Override
    public void onSearchSuccess(ArrayList<Experiment> results) {
        experiments.clear();
        experiments.addAll(results);
        expAdapter.notifyDataSetChanged();
    }
}
