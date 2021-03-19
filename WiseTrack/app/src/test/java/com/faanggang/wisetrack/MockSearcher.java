package com.faanggang.wisetrack;

import com.faanggang.wisetrack.experiment.Experiment;
import com.faanggang.wisetrack.experiment.ExperimentManager;
import com.faanggang.wisetrack.experiment.Searcher;
import com.faanggang.wisetrack.search.SearchManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MockSearcher implements Searcher {
    public ArrayList<Experiment> resultingExperiments;
    private SearchManager searchManager;
    private ExperimentManager expManager;

    public MockSearcher(FirebaseFirestore db) {
        resultingExperiments = new ArrayList<>();
        searchManager = new SearchManager(this, db);
        expManager = new ExperimentManager(this,db);
    }
    @Override
    public void onSearchSuccess(ArrayList<Experiment> results) {
        resultingExperiments.addAll(results);
    }

    public void makeSearchRequest(String query) {
        searchManager.searchForQuery(query);
    }

    public ArrayList<String> getKeywordsFromString(String query) {
        return searchManager.getKeywordsFromString(query);
    }

    public void userExperimentQueryRequest(String query) {
        expManager.userExpQuery(query);
    }

}
