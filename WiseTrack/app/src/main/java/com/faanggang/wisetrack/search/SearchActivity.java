package com.faanggang.wisetrack.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.faanggang.wisetrack.Experiment;
import com.faanggang.wisetrack.R;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ExperimentAdapter experimentAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Experiment> searchResults;
    private ImageButton searchButton;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchResults = new ArrayList<Experiment>();
        ArrayList<Experiment> cool = new ArrayList<Experiment>();

        experimentAdapter = new ExperimentAdapter(searchResults);

        searchButton = findViewById(R.id.activity_search_search_button);
        searchEditText = findViewById(R.id.activity_search_search_edit_text);
        recyclerView = findViewById(R.id.activity_search_list);
        recyclerView.setAdapter(experimentAdapter);
    }
}