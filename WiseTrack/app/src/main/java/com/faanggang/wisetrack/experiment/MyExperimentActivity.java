package com.faanggang.wisetrack.experiment;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.Experiment;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.search.ExperimentAdapter;

import java.util.ArrayList;

public class MyExperimentActivity extends AppCompatActivity implements UserExperimentManager.userExpFinder {
    private ExperimentAdapter expAdapter;
    private RecyclerView recyclerView;
    private UserExperimentManager expManager;
    private ArrayList<Experiment> experiments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("EXPERIMENT","We got here");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_experiments);
        expManager = new UserExperimentManager(this);

        experiments = new ArrayList<Experiment>();

        expAdapter= new ExperimentAdapter(experiments);

        recyclerView = findViewById(R.id.my_experiments_recyclerview);
        recyclerView.setAdapter(expAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
        expManager.userExpQuery("2j7WacgiS0RjjWEY5K6sgh3Dfx63");


    }
    @Override
    public void onUserExpFound(ArrayList<Experiment> results) {
        experiments.clear();
        experiments.addAll(results);
        expAdapter.notifyDataSetChanged();
    }
}
