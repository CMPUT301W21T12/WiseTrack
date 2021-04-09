package com.faanggang.wisetrack.controllers;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * This class controls the creation and storing process of BlockedExperimenters sub-collection inside
 * Experiments collection which stores a list of blocked experimenter.
 * Blocked experimenters are restricted from executing trials of the current experiment.
 */
public class BlockExperimenterController {
    private CollectionReference blockedExperimentersCollectionReference;
    private FirebaseFirestore db;
    private String expID;  // experiment id of the current trial
    private String conductorID;  // the current trial's conductor id

    /**
     * @param conductorID: experiment conductor ID of the current trial
     */
    public BlockExperimenterController(String conductorID, String expID) {
        this.conductorID = conductorID;
        this.expID = expID;
        db = FirebaseFirestore.getInstance();
        blockedExperimentersCollectionReference = db
                .collection("Experiments").document(expID)
                .collection("BlockedExperimenters");
    }

    public Map createBlockedExperimenterDocument(String conductorID) {
        Map<String, Object> data = new HashMap<>();
        data.put("experimenterId", conductorID);
        return data;
    }

    public void blockExperimenter(Map<String, Object> experimenterDoc) {
        blockedExperimentersCollectionReference
                .add(experimenterDoc)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("BlockExperimenterController", "BlockedExperimenterDoc DocumentSnapshot " +
                                "successfully written with ID:" + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("BlockExperimenterController", "Error adding BlockedExperimenterDoc document:", e);
                    }
                });
    }
}
