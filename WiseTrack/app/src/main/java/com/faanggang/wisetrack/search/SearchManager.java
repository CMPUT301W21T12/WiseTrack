package com.faanggang.wisetrack.search;

import com.faanggang.wisetrack.experiment.Experiment;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class controls searching the database for experiments.
 */

public class SearchManager {
    private FirebaseFirestore db;
    private Searcher searcher;

    /**
     * This is an interface that is implemented by Activities that forces implementing activities
     * to implement a method to be called upon a successful search.
     */
    public interface Searcher {
        void onSearchSuccess(List<Experiment> results);
    }

    /**
     * This is a constructor that instantiates the FireBase instance and sets the SearchManager's
     * current searcher.
     * @param searcher
     * searcher is the activity that will be using this instance of SearchManager
     * @param db
     * injected database to send requests to
     */
    public SearchManager(Searcher searcher, FirebaseFirestore db) {
        this.db = db;
        this.searcher = searcher;
    }

    /**
     * This method searches the database for keywords contained in a string and calls the
     * searcher's onSearchSuccess method with relevant experiments upon successful search.
     * @param query
     * query is a string that contains keywords to search for in the database.
     */
    public void searchForQuery(String query) {

        CollectionReference experiments = db.collection("Experiments");
        ArrayList<Experiment> results;

        ArrayList<String> queryKeywords = getKeywordsFromString(query);

        db.collection("Experiments").whereArrayContainsAny("keywords", queryKeywords)
                .orderBy("datetime")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Experiment> searchResults = new ArrayList<Experiment>();
                        List<DocumentSnapshot> result = task.getResult().getDocuments();
                        for (DocumentSnapshot snapshot : result) {
                            Experiment exp = new Experiment(snapshot.getString("name"),
                                    snapshot.getString("description"),
                                    snapshot.getString("region"),
                                    snapshot.getLong("minTrials").intValue(),
                                    snapshot.getLong("crowdSource").intValue(),
                                    snapshot.getBoolean("geolocation"),
                                    snapshot.getDate("datetime"),
                                    snapshot.getString("uID"));
                            exp.setExpID(snapshot.getId());
                            searchResults.add(exp);
                            exp.setOpen(snapshot.getBoolean("open"));
                        }
                        searcher.onSearchSuccess(searchResults);
                    } else {
                    }
                })
                .addOnFailureListener(e -> {
                });
    }

    /**
     * This method creates an ArrayList of keywords from a passed string.
     * @param query
     * query is a string that contains keywords
     */
    public ArrayList<String> getKeywordsFromString(String query) {
        ArrayList<String> queryKeywords = new ArrayList<>();
        queryKeywords.addAll(Arrays.asList(query.split(" ")));
        // make all of the capital
        for (int i = 0; i < queryKeywords.size(); i++) {
            queryKeywords.set(i, queryKeywords.get(i).toUpperCase());
        }
        if (queryKeywords.size() > 10) {
            queryKeywords.subList(0, 10);
        }
        return queryKeywords;
    }
}
