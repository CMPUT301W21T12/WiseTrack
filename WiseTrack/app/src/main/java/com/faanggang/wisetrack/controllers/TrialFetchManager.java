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

public class TrialFetchManager {
    private TrialFetcher fetcher;
    private FirebaseFirestore db;
    private int trialType;

    public TrialFetchManager(TrialFetcher fetcher) {
        this.db = FirebaseFirestore.getInstance();
        this.fetcher = fetcher;
    }

    public interface TrialFetcher {
        void onSuccessfulFetch(ArrayList<Trial> trials);
    }

    /**
     * This method fetches trial documents that belong to a given experiment document from the Cloud Firestore.
     * This method does not return anything. It instead calls on an interface method implemented
     * by the Object that is receiving the data.
     * @param expID
     *     expID is the ID of the experiment that you are fetching its trials from.
     */
    /*public void fetchTrials(String expID){
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
                                                    docSnapshot.getDouble("result"),
                                                    docSnapshot.getString("geolocation"),
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
    }*/


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

                                trials.add(this.createTrial(
                                        trialType,
                                        docSnapshot.getDouble("result"),
                                        geolocation,
                                        docSnapshot.getString("conductor id"),
                                        docSnapshot.getDate("date"))
                                );
                            }
                            fetcher.onSuccessfulFetch(trials);
                        }
                    } else {
                        Log.w("TRIAL","DID NOT FIND");
                    }
                });
    }

    public Trial createTrial(int trialType, double trialResult, Location trialGeolocation, String conductorID, Date date) {
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
    }
}
