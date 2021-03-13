package com.faanggang.wisetrack.experiment;

import android.provider.DocumentsContract;
import android.util.Log;

import com.faanggang.wisetrack.Experiment;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

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


    public void userExpQuery(String userID) {
        db.collection("Experiments").whereEqualTo("ownerID", userID).get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                ArrayList<Experiment> results = new ArrayList<Experiment>();
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                for(DocumentSnapshot doc: documents){
                    Log.w("EXPERIMENT", doc.getString("name"));
                   results.add(new Experiment(
                           doc.getString("name"),
                           doc.getString("description"),
                           doc.getString("region"),
                           doc.getLong("minTrials").intValue(),
                           doc.getLong("crowdSource").intValue(),
                           doc.getBoolean("geolocation"),
                           doc.getDate("date"),
                           doc.getString("ownerID")
                           ));
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
