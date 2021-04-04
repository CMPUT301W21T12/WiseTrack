package com.faanggang.wisetrack.controllers;

import android.provider.DocumentsContract;

import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.model.experiment.Searcher;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * This class controls searching the database for experiments.
 */

public class SearchManager {
    private FirebaseFirestore db;
    private Searcher searcher;

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
        db.collection("Experiments")
                .orderBy("datetime")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<DocumentSnapshot> result
                                = (ArrayList<DocumentSnapshot>) task.getResult().getDocuments();
                        ArrayList<Experiment> searchResults = getQueriedExperiments(result, query);
                        searcher.onSearchSuccess(searchResults);
                    }
                })
                .addOnFailureListener(e -> {
                });
    }

    public Experiment createExperimentFromSnapshot(DocumentSnapshot snapshot) {
        Experiment experiment = new Experiment(snapshot.getString("name"),
                snapshot.getString("description"),
                snapshot.getString("region"),
                snapshot.getLong("minTrials").intValue(),
                snapshot.getLong("trialType").intValue(),
                snapshot.getBoolean("geolocation"),
                snapshot.getDate("datetime"),
                snapshot.getString("uID"));
        experiment.setExpID(snapshot.getId());
        if (snapshot.getString("username") != null) {
            experiment.setUsername(snapshot.getString("username"));
        } else {
            experiment.setUsername("username");
        }

        return experiment;
    }

    public ArrayList<Experiment> getQueriedExperiments(ArrayList<DocumentSnapshot> documentSnapshots, String query) {
        ArrayList<Experiment> experiments = new ArrayList<>();

        for (DocumentSnapshot snapshot : documentSnapshots) {
            if (snapshot.getString("name").toLowerCase(Locale.ENGLISH).contains(query)
                || snapshot.getString("description").toLowerCase(Locale.ENGLISH).contains(query)
                || (snapshot.getBoolean("open") == true && query.contains("open"))
                || (snapshot.getBoolean("open") == false && query.contains("closed"))
                || (snapshot.getString("username") != null
                    && snapshot.getString("username").toLowerCase(Locale.ENGLISH).contains(query))){
                experiments.add(createExperimentFromSnapshot(snapshot));
            }
        }
        return experiments;
    }
}
