package com.faanggang.wisetrack.controllers;

import android.util.Log;

import com.faanggang.wisetrack.model.executeTrial.BinomialTrial;
import com.faanggang.wisetrack.model.executeTrial.CountTrial;
import com.faanggang.wisetrack.model.executeTrial.MeasurementTrial;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

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
     * This method fetches trials of a given experiment from the Cloud Firestore.
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
                                            Log.w("TRIALS", docSnapshot.getString("result"));
                                            trials.add(createTrial(
                                                    trialType,
                                                    docSnapshot.getDouble("result"),
                                                    docSnapshot.getString("geolocation"),
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
        db.collection("Experiments")
                .document(expID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        trialType = task.getResult().getLong("trialType").intValue();
                    } else {
                        Log.w("EXPERIMENT","DID NOT FIND");
                    }
                });
    }

    public void fetchTrials(String expID){
        db.collection("Experiments")
                .document(expID)
                .collection("Trials")
                .get()
                .addOnCompleteListener(task1 -> {
                    if (task1.isSuccessful()) {
                        ArrayList<Trial> trials = new ArrayList<>();
                        fetchTrialType(expID);
                        for (DocumentSnapshot docSnapshot : task1.getResult().getDocuments()) {
                            Log.w("TRIALS", docSnapshot.getString("result"));
                            trials.add(createTrial(
                                    trialType,
                                    docSnapshot.getDouble("result"),
                                    docSnapshot.getString("geolocation"),
                                    docSnapshot.getString("conductor id"),
                                    docSnapshot.getDate("date"))
                            );
                        }
                        fetcher.onSuccessfulFetch(trials);
                    } else {
                        Log.w("TRIAL","DID NOT FIND");
                    }
                });
    }


    public Trial createTrial(int trialType, double trialResult, String trialGeolocation, String conductorID, Date date) {
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
