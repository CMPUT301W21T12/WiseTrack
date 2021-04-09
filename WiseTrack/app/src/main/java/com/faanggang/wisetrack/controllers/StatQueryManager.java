package com.faanggang.wisetrack.controllers;

import com.faanggang.wisetrack.model.experiment.Searcher;
import com.google.firebase.firestore.FirebaseFirestore;

public class StatQueryManager {
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
    public StatQueryManager(Searcher searcher, FirebaseFirestore db) {
        this.db = db;
        this.searcher = searcher;
    }

}
