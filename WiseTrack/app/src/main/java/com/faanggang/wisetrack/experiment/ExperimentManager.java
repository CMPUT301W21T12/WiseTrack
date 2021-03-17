package com.faanggang.wisetrack.experiment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ExperimentManager {
    private FirebaseFirestore db;

    public ExperimentManager(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void getUsername(String uID, OnCompleteListener<DocumentSnapshot> callback){
        db.collection("Users").document(uID)
                .get()
                .addOnCompleteListener(callback);
    }
}
