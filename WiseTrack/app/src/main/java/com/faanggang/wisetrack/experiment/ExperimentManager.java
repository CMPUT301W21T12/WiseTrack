package com.faanggang.wisetrack.experiment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
/**
 *  ExperimentManager is a controller class that is primarily focused on retrieving data from the Cloud Firestore.
 *  In the future we plan to merge ExperimentManager, PublishingManager, UserExperimentManager, and SearchManager into oen inclusive controller.
 */
public class ExperimentManager {
    private FirebaseFirestore db;

    public ExperimentManager(){
        this.db = FirebaseFirestore.getInstance();
    }

    /**
     * This method is an asynchronous method to retrieve the information from one specific experiment.
     * @param expID
     * expID is the FirebaseID of the document that we are querying for.
     * @param callback
     * callback is a function that has 1 parameter that will run at the end of this method.
     */
    public void getExperimentInfo(String expID, OnCompleteListener<DocumentSnapshot> callback){
        db.collection("Experiments").document(expID)
            .get().addOnCompleteListener(callback);
    }
}

