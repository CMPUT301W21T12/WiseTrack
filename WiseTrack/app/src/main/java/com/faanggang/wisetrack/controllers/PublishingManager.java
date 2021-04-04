package com.faanggang.wisetrack.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * This class controls publishing an experiment to our Firestore database.
 */
public class PublishingManager {
    private CollectionReference experimentCollectionReference;
    private FirebaseFirestore db;

    /**
     * This constructor initializes this instance's database with the passed one.
     * @param db
     * db is the FirebaseFirestore to be used as the database for this instance.
     */
    public PublishingManager(FirebaseFirestore db) {
        this.db = db;
        experimentCollectionReference = db.collection("Experiments");
    }

    /**
     * This method publishes the experiment to the Firestore database.
     * @param experimentData
     * experimentData is a map of values to be published to the database as an experiment.
     */
    public void publishExperiment(Map<String, Object> experimentData) throws Exception {
        experimentCollectionReference
                .add(experimentData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("PublishController", "DocumentSnapshot written with ID:"
                                + documentReference.getId());
                        UserManager uM = new UserManager(db);
                        uM.addExperimentReference(WiseTrackApplication.getCurrentUser().getUserID(),
                                documentReference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("PublishController", "Error adding document:", e);
                    }
                });
    }

    /**
     * This method creates a map of values necessary for experiment in the database based on
     * the Experiment object's values.
     * @param experiment
     * experiment is an Experiment object whose variables are to be used to values in the map.
     * @return
     * a Map that has all necessary key-value pairs is returned.
     */
    public Map createExperimentMap(Experiment experiment, String userName) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", experiment.getName());
        data.put("description", experiment.getDescription());
        data.put("region", experiment.getRegion());
        data.put("minTrials", experiment.getMinTrials());
        data.put("trialType", experiment.getTrialType());
        data.put("geolocation", experiment.getGeolocation());
        data.put("datetime", new Timestamp(experiment.getDate()));
        data.put("uID", experiment.getOwnerID());
        data.put("open", true); // open by default
        data.put("published", true); // published by default
        data.put("subscribers", new ArrayList<String>());
        data.put("username", userName);

        return data;
    }
    
}
