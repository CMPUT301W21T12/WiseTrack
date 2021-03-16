package com.faanggang.wisetrack;

import com.faanggang.wisetrack.search.SearchManager;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MockSearcher implements SearchManager.Searcher {
    public List<Experiment> resultingExperiments;
    private SearchManager searchManager;

    public MockSearcher(FirebaseFirestore db) {
        resultingExperiments = new ArrayList<>();
        searchManager = new SearchManager(this, db);
    }
    @Override
    public void onSearchSuccess(List<Experiment> results) {
        resultingExperiments.addAll(results);
    }

    public void makeSearchRequest(String query) {
        searchManager.searchForQuery(query);
    }

    public ArrayList<String> getKeywordsFromString(String query) {
        return searchManager.getKeywordsFromString(query);
    }

}
