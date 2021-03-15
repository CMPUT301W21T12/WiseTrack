package com.faanggang.wisetrack.publish;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faanggang.wisetrack.Experiment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PublishingController {
    private CollectionReference experimentCollectionReference;
    private FirebaseFirestore db;

    public PublishingController() {
        db = FirebaseFirestore.getInstance();
        experimentCollectionReference = db.collection("Experiments");
    }

    public void publishExperiment(Map<String, Object> experimentData) throws Exception {
        experimentCollectionReference
                .add(experimentData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("PublishController", "DocumentSnapshot written with ID:"
                                + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("PublishController", "Error adding document:", e);
                    }
                });
    }

    public Map createExperimentHashMap(Experiment experiment) {
        Map<String, Object> data = new HashMap<>();
        data.put("name", experiment.getName());
        data.put("description", experiment.getDescription());
        data.put("region", experiment.getRegion());
        data.put("minTrials", experiment.getMinTrials());
        data.put("crowdSource", experiment.getCrowdSource());
        data.put("geolocation", experiment.getGeolocation());
        data.put("datetime", new Timestamp(experiment.getDate()));
        data.put("uID", experiment.getOwnerID());
        data.put("trialType", 1); // default trial type
        data.put("open", true); // open by default
        // keywords.addAll(Arrays.asList(description.split(" "))); perhaps?
        ArrayList<String> keywords = new ArrayList<>();
        keywords.addAll(Arrays.asList(experiment.getName().split(" ")));
        for (int i = 0; i < keywords.size(); i++) {
            keywords.set(i, keywords.get(i).toUpperCase());
        }
        data.put("keywords", keywords);

        return data;
    }

    public void setExperimentCollectionReference(CollectionReference experimentCollectionReference) {
        this.experimentCollectionReference = experimentCollectionReference;
    }
}
