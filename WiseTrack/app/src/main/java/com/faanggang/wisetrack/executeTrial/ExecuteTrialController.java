package com.faanggang.wisetrack.executeTrial;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ExecuteTrialController {
    private CollectionReference trialCollectionReference;
    private FirebaseFirestore db;
    private String experimentID;

    public ExecuteTrialController() {
        db = FirebaseFirestore.getInstance();
        trialCollectionReference = db
                .collection("Experiments").document(experimentID)
                .collection("Trials");
    }
}
