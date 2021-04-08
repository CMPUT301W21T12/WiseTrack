package com.faanggang.wisetrack.controllers;

import android.location.Location;
import android.util.Log;

import com.faanggang.wisetrack.model.executeTrial.BinomialTrial;
import com.faanggang.wisetrack.model.executeTrial.CountTrial;
import com.faanggang.wisetrack.model.executeTrial.MeasurementTrial;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;

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
    private int trialType;

    /**
     * This constructor initializes instance variables via dependency injection.
     * @param fetcher
     * fetcher to be initialized
     */
    public TrialFetchManager(TrialFetcher fetcher) {
        this.db = FirebaseFirestore.getInstance();
        this.fetcher = fetcher;
    }

    /**
     * This interface is to be implemented by classes that use this class to fetch so as to
     * allow for asyncronous updating.
     */
    public interface TrialFetcher {
        void onSuccessfulFetch(ArrayList<Trial> trials);
    }

    /**
     * This helper method fetches trial type from the given experiment document.
     * @param expID: ID of the experiment that you are fetching its trials from.
     */
    public void fetchTrialType(String expID){
        db.collection("Experiments").document(expID).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        trialType = task.getResult().getLong("trialType").intValue();
                    } else {
                        Log.w("EXPERIMENT","DID NOT FIND");
                    }
                });
    }

    /**
     * This method fetches trial documents that belong to a given experiment document from the Cloud Firestore.
     * This method does not return anything. It instead calls on an interface method implemented
     * by the Object that is receiving the data.
     * @param expID: ID of the experiment that you are fetching its trials from.
     */
    public void fetchTrials(String expID){
        db.collection("Experiments").document(expID).collection("Trials")
        .orderBy("date", Query.Direction.DESCENDING).get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.e("", String.valueOf(task.getResult().size()));
                        if (task.getResult().size() != 0) {
                            ArrayList<Trial> trials = new ArrayList<>();
                            this.fetchTrialType(expID);
                            List<DocumentSnapshot> docSnapList = task.getResult().getDocuments();
                            for (DocumentSnapshot docSnapshot : docSnapList) {
                                // fetch geolocation field data
                                GeoPoint location = docSnapshot.getGeoPoint("geolocation");
                                // create a Location object
                                Location geolocation = new Location("");
                                if (location != null) {
                                    double lat = location.getLatitude();
                                    double lon = location.getLongitude();
                                    geolocation.setLatitude(lat);
                                    geolocation.setLongitude(lon);
                                } else {
                                    geolocation = null;
                                }

                                trials.add(new Trial(
                                        geolocation,
                                        docSnapshot.getString("conductor id"),
                                        docSnapshot.getDate("date"),
                                        docSnapshot.getDouble("result"),
                                        trialType));
                            }
                            fetcher.onSuccessfulFetch(trials);
                        }
                    } else {
                        Log.w("TRIAL","DID NOT FIND");
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
    /*public Trial createTrial(int trialType, double trialResult, Location trialGeolocation, String conductorID, Date date) {
        Trial trial;
        if ((trialType == 0)||(trialType == 2)) {  // count and NNIC type trials
            trial = new CountTrial(trialResult, trialGeolocation, conductorID, date, trialType);
        } else if (trialType == 1) {
            trial = new BinomialTrial(trialResult, trialGeolocation, conductorID, date, trialType);
        } else if (trialType == 3) {
            trial = new MeasurementTrial(trialResult, trialGeolocation, conductorID, date, trialType);
        } else {
            trial = null;  // this might not be necessary
        }
        return trial;
    }*/
}
