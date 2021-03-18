package com.faanggang.wisetrack.search;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.faanggang.wisetrack.experiment.Experiment;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.adapters.ExperimentAdapter;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * Activity that provides a frontend to searching in the form of a RecyclerView and a textbox
 * that allows users to input keyword based queries.
 */
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
        searchManager = new SearchManager(this, FirebaseFirestore.getInstance());

        searchResults = new ArrayList<Experiment>();

        experimentAdapter = new ExperimentAdapter(this,searchResults);

        resultCount = findViewById(R.id.activity_search_count_TextView);
        searchButton = findViewById(R.id.activity_search_search_button);
        searchEditText = findViewById(R.id.activity_search_search_edit_text);
        recyclerView = findViewById(R.id.activity_search_list);
        recyclerView.setAdapter(experimentAdapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
    }

    /**
     * This method makes the RecyclerView display passed Experiments and updates TextView
     * showing the amount of results found
     * @param results
     * results are experiments to display which were successfully found on a search
     */
    @Override
    public void onSearchSuccess(List<Experiment> results) {
        searchResults.clear();
        searchResults.addAll(results);
        resultCount.setText(searchResults.size() + " result(s) found");
        experimentAdapter.notifyDataSetChanged();
    }

    /**
     * This is an onClick method that starts a search query from a SearchManager object.
     * @param view
     * view is the view that the experiment
     */
    public void onSearch(View view) {
        Log.w("SEARCH", "onSearch");
        searchManager.searchForQuery(searchEditText.getText().toString());
    }
}