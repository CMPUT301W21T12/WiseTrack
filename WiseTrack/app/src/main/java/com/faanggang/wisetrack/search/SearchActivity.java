package com.faanggang.wisetrack.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.faanggang.wisetrack.Experiment;
import com.faanggang.wisetrack.R;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchManager.Searcher {
    private ExperimentAdapter experimentAdapter;
    private RecyclerView recyclerView;
    private ArrayList<Experiment> searchResults;
    private ImageButton searchButton;
    private EditText searchEditText;
    private SearchManager searchManager;
    private TextView resultCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchManager = new SearchManager(this);

        searchResults = new ArrayList<Experiment>();

        experimentAdapter = new ExperimentAdapter(searchResults);

        resultCount = findViewById(R.id.activity_search_count_TextView);
        searchButton = findViewById(R.id.activity_search_search_button);
        searchEditText = findViewById(R.id.activity_search_search_edit_text);
        recyclerView = findViewById(R.id.activity_search_list);
        recyclerView.setAdapter(experimentAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onSearchSuccess(List<Experiment> results) {
        searchResults.clear();
        searchResults.addAll(results);
        resultCount.setText(searchResults.size() + " result(s) found");
        experimentAdapter.notifyDataSetChanged();
    }

    public void onSearch(View view) {
        Log.w("SEARCH", "onSearch");
        searchManager.searchForQuery(searchEditText.getText().toString());
    }
}