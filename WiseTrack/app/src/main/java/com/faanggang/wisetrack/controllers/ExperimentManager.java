package com.faanggang.wisetrack.controllers;

import android.util.Log;

import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.model.experiment.Searcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 *  ExperimentManager is a controller class that is primarily focused on retrieving data from the Cloud Firestore.
 *  In the future we plan to merge ExperimentManager, PublishingManager, UserExperimentManager, and SearchManager into oen inclusive controller.
 */
public class ExperimentManager {
    private FirebaseFirestore db;
    private Searcher searcher;
    public ExperimentManager(){
        this.db = FirebaseFirestore.getInstance();
    }
    public ExperimentManager(Searcher searcher, FirebaseFirestore db){
        this.db = db;
        this.searcher = searcher;
    }
    public ExperimentManager(Searcher searcher){
        this.db = FirebaseFirestore.getInstance();
        this.searcher = searcher;
    }

    /**
     * This method is an asynchronous method to retrieve the information from one specific experiment.
     * @param expID
     * expID is the FirebaseID of the document that we are querying for.
     * @param callback
     * callback is a function that has 1 parameter that will run at the end of this method.
     */
    public void getExperimentInfo(String expID, OnCompleteListener<DocumentSnapshot> callback){
        db.collection("Experiments").document(expID)
            .get().addOnCompleteListener(callback);
    }

    /**
     * This method queries the Cloud Firestore to find experiments that have been published by a specific user.
     * Does not return any value, and results must be passed to a callback function implemented by the userExpFinder interface.
     * @param userID
     * userID is the Firestore documentID of the user.
     */
    public void userExpQuery(String userID) {
        db.collection("Experiments").whereEqualTo("uID", userID).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ArrayList<Experiment> results = new ArrayList<Experiment>();
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();
                        for(DocumentSnapshot doc: documents){
                            Experiment e = new Experiment(
                                    doc.getString("name"),
                                    doc.getString("description"),
                                    doc.getString("region"),
                                    doc.getLong("minTrials").intValue(),
                                    doc.getLong("trialType").intValue(),
                                    doc.getBoolean("geolocation"),
                                    doc.getDate("datetime"),
                                    doc.getString("uID")
                            );
                            e.setOpen(doc.getBoolean("open"));
                            e.setExpID(doc.getId());
                            if (doc.getString("username") != null) {
                                e.setUsername(doc.getString("username"));
                            } else {
                                e.setUsername("username");
                            }
                            results.add(e);
                            searcher.onSearchSuccess(results);
                        }
                    } else{
                        Log.w("EXPERIMENT","DID NOT FIND");
                    }
                })
                .addOnFailureListener(e ->{
                    Log.w("EXPERIMENT", e.toString());
                })
        ;
    }
}

