package com.faanggang.wisetrack.experiment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faanggang.wisetrack.Experiment;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.search.ExperimentAdapter;

import java.util.ArrayList;

public class MyExperimentActivity extends AppCompatActivity {
    private ArrayList<Experiment> experiments;
    private ExperimentAdapter expAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_experiments);
        experiments = new ArrayList<Experiment>();
        expAdapter= new ExperimentAdapter(experiments);
        recyclerView = findViewById(R.id.my_experiments_recyclerview);
        recyclerView.setAdapter(expAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        );
    }
}
