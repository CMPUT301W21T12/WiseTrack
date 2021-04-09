package com.faanggang.wisetrack.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * This class controls the execution and storing process of Trials sub-collection inside
 * Experiments collection which stores the current experiment's trials.
 * Blocked experimenters are restricted from executing trials of the current experiment.
 *     trialCollectionReference: Collection reference to trials collection.
 *     db: Firebase Reference
 *     experimentID: Document id of current experiment.
 */
public class ExecuteTrialController {
    private final CollectionReference trialCollectionReference;
    private FirebaseFirestore db;
    private String experimentID;

    /**
     * @param expID experiment ID of current trial
     */
    public ExecuteTrialController(String expID) {
        experimentID = expID;
        db = FirebaseFirestore.getInstance();
        trialCollectionReference = db
                .collection("Experiments").document(experimentID)
                .collection("Trials");
    }

    /**
     * @param trial current trial to be inserted into Cloud Firebase
     * @return data hashmap trial object ready to be inserted
     */
    public Map createTrialDocument(Trial trial) {
        Map<String, Object> data = new HashMap<>();
        data.put("result", trial.getTrialResult());
        data.put("geolocation", trial.getTrialGeolocation());
        if (trial.getTrialGeolocation() == null) {
            data.put("geolocation", null);
        } else {
            data.put("geolocation", new GeoPoint(trial.getTrialGeolocation().getLatitude(), trial.getTrialGeolocation().getLongitude()));
        }
        data.put("date", trial.getDatetime());
        data.put("conductor id", trial.getExperimenterID());
        //data.put("trial id", trial.getTrialID());

        return data;
    }

    /**
     * @param trial the trial to be stored into Firebase.
     */
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
