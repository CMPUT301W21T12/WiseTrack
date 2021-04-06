package com.faanggang.wisetrack.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faanggang.wisetrack.model.executeTrial.BinomialTrial;
import com.faanggang.wisetrack.model.executeTrial.CountTrial;
import com.faanggang.wisetrack.model.executeTrial.MeasurementTrial;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ExecuteTrialController {
    private CollectionReference trialCollectionReference;
    private FirebaseFirestore db;
    private String experimentID;

    /**
     * @param expID: experiment ID of current trial
     */
    public ExecuteTrialController(String expID) {
        experimentID = expID;
        db = FirebaseFirestore.getInstance();
        trialCollectionReference = db
                .collection("Experiments").document(experimentID)
                .collection("Trials");
    }

    /**
     * @param trial: count OR non-negative integer count type trial
     * @return data: hashmap trial object ready to be inserted
     */
    public Map createTrialDocument(CountTrial trial) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", trial.getCount());
        data.put("geolocation", trial.getTrialGeolocation());
        data.put("date", trial.getDatetime());
        data.put("conductor id", trial.getExperimenterID());
        //data.put("trial id", trial.getTrialID());

        return data;
    }

    /**
     * @param trial: binomial type trial
     * @return data: hashmap trial object ready to be inserted
     */
    public Map createTrialDocument(BinomialTrial trial) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", trial.getTrialResult());
        data.put("geolocation", trial.getTrialGeolocation());
        data.put("date", trial.getDatetime());
        data.put("conductor id", trial.getExperimenterID());
        //data.put("trial id", trial.getTrialID());

        return data;
    }

    /**
     * @param trial: measurement type trial
     * @return data: hashmap trial object ready to be inserted
     */
    public Map createTrialDocument(MeasurementTrial trial) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", trial.getMeasurement());
        data.put("geolocation", trial.getTrialGeolocation());
        data.put("date", trial.getDatetime());
        data.put("conductor id", trial.getExperimenterID());
        //data.put("trial id", trial.getTrialID());

        return data;
    }

    public void executeTrial(Map<String, Object> trial) {
        trialCollectionReference
                .add(trial)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("ExecuteTrialController", "Trial DocumentSnapshot " +
                                "successfully written with ID:" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("ExecuteTrialController", "Error adding trial document:", e);
                    }
                });
    }
}
