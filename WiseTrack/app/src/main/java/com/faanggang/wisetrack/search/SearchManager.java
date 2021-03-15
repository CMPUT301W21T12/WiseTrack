package com.faanggang.wisetrack.search;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faanggang.wisetrack.Experiment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class controls searching the database.
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
     */
    public SearchManager(Searcher searcher) {
        db = FirebaseFirestore.getInstance();
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
        Log.w("SEARCH", "starting search for query");

        ArrayList<String> queryKeywords = new ArrayList<>();
        queryKeywords.addAll(Arrays.asList(query.split(" ")));
        // make all of the capital
        for (int i = 0; i < queryKeywords.size(); i++) {
            queryKeywords.set(i, queryKeywords.get(i).toUpperCase());
        }
        if (queryKeywords.size() > 10) {
            queryKeywords.subList(0, 10);
        }
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
                        Log.w("SEARCH", "EXCEPTION: " + task.getException().toString());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("SEARCH", "FAILURE: " + e.toString());
                });
    }
}
