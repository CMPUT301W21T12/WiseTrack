package com.faanggang.wisetrack.experiment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ExperimentManager {
    private FirebaseFirestore db;

    public ExperimentManager(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void getExperimentInfo(String expID, OnCompleteListener<DocumentSnapshot> callback){
        db.collection("Experiments").document(expID)
            .get().addOnCompleteListener(callback);
    }
}

