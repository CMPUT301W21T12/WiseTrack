package com.faanggang.wisetrack.experiment;

import android.util.Log;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

/**
 * UserExperimentManager deals with connecting a user with what experiments they have published.
 */

public class UserExperimentManager {
    private FirebaseFirestore db;
    private userExpFinder finder;

    public interface userExpFinder {
        void onUserExpFound(ArrayList<Experiment> results);
    }
    public UserExperimentManager(userExpFinder finder){
        this.db = FirebaseFirestore.getInstance();
        this.finder = finder;
    }

    /**
     * This method queries the Cloud Firestore to find experiments that have been published by a specific user.
     * Does not return any value, and results must be passed to a callback function implemented by the userExpFinder interface.
     * @param userID
     * userID is the Firestore documentID of the user.
     */
    public void userExpQuery(String userID) {
        db.collection("Experiments").whereEqualTo("uID", userID).get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Experiment> results = new ArrayList<Experiment>();
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                for(DocumentSnapshot doc: documents){
                    Log.w("EXPERIMENT", doc.getString("name"));
                    Experiment e = new Experiment(
                            doc.getString("name"),
                            doc.getString("description"),
                            doc.getString("region"),
                            doc.getLong("minTrials").intValue(),
                            doc.getLong("trialType").intValue(),
                            doc.getBoolean("geolocation"),
                            doc.getDate("datetime"),
                            doc.getString("uID")
                    );
                    e.setOpen(doc.getBoolean("open"));
                    e.setExpID(doc.getId());
                    results.add(e);
                    finder.onUserExpFound(results);
                }
            } else{
                Log.w("EXPERIMENT","DID NOT FIND");
            }
        })
        .addOnFailureListener(e ->{
            Log.w("EXPERIMENT", e.toString());
        })
        ;
    }

}
