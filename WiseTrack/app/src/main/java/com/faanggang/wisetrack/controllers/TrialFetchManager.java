package com.faanggang.wisetrack.controllers;

import android.location.Location;
import android.util.Log;

import com.faanggang.wisetrack.model.executeTrial.BinomialTrial;
import com.faanggang.wisetrack.model.executeTrial.CountTrial;
import com.faanggang.wisetrack.model.executeTrial.MeasurementTrial;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class controls fetching of trials.
 */
public class TrialFetchManager {
    private TrialFetcher fetcher;
    private FirebaseFirestore db;

    /**
     * This constructor initializes instance variables via dependency injection.
     * @param db
     * FirebaseFirestore to be initialized
     * @param fetcher
     * fetcher to be initialized
     */
    public TrialFetchManager(FirebaseFirestore db, TrialFetcher fetcher) {
        this.db = db;
        this.fetcher = fetcher;
    }

    /**
     * This interface is to be implemented by classes that use this class to fetch so as to
     * allow for asyncronous updating.
     */
    public interface TrialFetcher {
        public void onSuccessfulFetch(ArrayList<Trial> trials);
    }

    /**
     * This method queries the Cloud Firestore to find experiments that have been subscribed to by a specific user.
     * @param expID
     * expID is the ID of the experiment that you are adding to.
     */
    public void fetchTrials(String expID){
        db.collection("Experiments")
                .document(expID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        int trialType = task.getResult().getLong("trialType").intValue();
                        db.collection("Experiments")
                                .document(expID)
                                .collection("Trials")
                                .get()
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        ArrayList<Trial> trials = new ArrayList<>();
                                        for (DocumentSnapshot docSnapshot : task1.getResult().getDocuments()) {
                                            Object locationObj = docSnapshot.get("geolocation");
                                            if (locationObj instanceof String || locationObj instanceof Map) {
                                                continue;
                                            }
                                            GeoPoint geoPoint = (GeoPoint) locationObj;
                                            Location location = null;
                                            if (geoPoint != null) {
                                                location = new Location("");
                                                location.setLatitude(geoPoint.getLatitude());
                                                location.setLongitude(geoPoint.getLongitude());
                                            }
                                            trials.add(createTrial(
                                                    trialType,
                                                    docSnapshot.getLong("result"),
                                                    location,
                                                    docSnapshot.getString("conductor id"),
                                                    docSnapshot.getDate("date"))
                                            );
                                        }
                                        fetcher.onSuccessfulFetch(trials);
                                    }
                                });
                    }
                });
    }

    /**
     * createTrial creates a trial with the given arguments of the type dictated by trialType
     * @param trialType
     * trialType dictates the kind of trial that this is
     * @param trialResult
     * trialResult is the value associated to that trial
     * @param trialGeolocation
     * trialGeolocation is where the trial occurred. It is null if geolocation is not required
     * @param conductorID
     * conductorID is the UUID of the user who conducted the experiment
     * @param date
     * date is that Date associaetd with the experiment
     * @return
     */
    public Trial createTrial(int trialType, float trialResult, Location trialGeolocation, String conductorID, Date date) {
        Trial trial;
        if ((trialType == 0)||(trialType == 2)) {  // count and NNIC type trials
            trial = new CountTrial((int)trialResult, trialGeolocation, conductorID, date);
        } else if (trialType == 1) {
            trial = new BinomialTrial((int)trialResult, trialGeolocation, conductorID, date);
        } else if (trialType == 3) {
            trial = new MeasurementTrial(trialResult, trialGeolocation, conductorID, date);
        } else {
            trial = null;  // this might not be necessary
        }
        return trial;
    }
}
