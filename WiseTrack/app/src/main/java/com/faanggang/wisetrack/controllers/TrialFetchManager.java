package com.faanggang.wisetrack.controllers;

import com.faanggang.wisetrack.model.executeTrial.BinomialTrial;
import com.faanggang.wisetrack.model.executeTrial.CountTrial;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class TrialFetchManager {
    private TrialFetcher fetcher;

    private FirebaseFirestore db;
    TrialFetchManager(FirebaseFirestore db, TrialFetcher fetcher) {
        this.db = db;
        this.fetcher = fetcher;
    }

    public interface TrialFetcher {
        public void onSuccessfulFetch(ArrayList<Trial> trials);
    }

    /**
     * This method updates the "Subscription" field of a user on the firebase.
     * @param expID
     * expID is the ID of the experiment that you are adding to.
     */
    public void fetchTrials(String expID){
        db.collection("Experiments")
                .document(expID)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        int trialType = task.getResult().getLong("trialType").intValue();
                        db.collection("Experiments")
                                .document(expID)
                                .collection("Trials")
                                .get()
                                .addOnCompleteListener(task1 -> {
                                    ArrayList<Trial> trials = new ArrayList<>();
                                    for (DocumentSnapshot docSnapshot : task1.getResult().getDocuments()) {
                                        trials.add(createTrial(trialType, docSnapshot));
                                    }
                                    fetcher.onSuccessfulFetch(trials);
                                });
                    }
                });
    }

    public Trial createTrial(int trialType, DocumentSnapshot snapshot) {
    }
}
