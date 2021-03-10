package com.faanggang.wisetrack.search;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faanggang.wisetrack.Experiment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SearchManager {
    private FirebaseFirestore db;
    private Searcher searcher;

    public interface Searcher {
        void onSearchSuccess(List<Experiment> results);
    }

    public SearchManager(Searcher searcher) {
        db = FirebaseFirestore.getInstance();
        this.searcher = searcher;
    }

    public void searchForQuery(String query) {

        CollectionReference experiments = db.collection("Experiments");
        ArrayList<Experiment> results;
        Log.w("SEARCH", "searchForQuery");

        ArrayList<String> queryKeywords = new ArrayList<>();
        queryKeywords.addAll(Arrays.asList(query.split(" ")));

        if (queryKeywords.size() > 10) {
            queryKeywords.subList(0, 10);
        }
        Log.w("SEARCH", "got this far lol");


        db.collection("Experiments").whereArrayContainsAny("keywords", queryKeywords)
                .orderBy("date")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Experiment> searchResults = new ArrayList<Experiment>();
                        List<DocumentSnapshot> result = task.getResult().getDocuments();
                        for (DocumentSnapshot snapshot : result) {
                            searchResults.add(new Experiment(snapshot.getString("name"),
                                    snapshot.getString("description"),
                                    snapshot.getString("region"),
                                    snapshot.getLong("minTrials").intValue(),
                                    snapshot.getLong("crowdSource").intValue(),
                                    snapshot.getBoolean("geolocation"),
                                    snapshot.getDate("date"),
                                    snapshot.getString("ownerID")));
                        }
                        searcher.onSearchSuccess(searchResults);
                    } else {
                        Log.w("COMPLETED:FAILURE", task.getException().toString());
                    }
                })
                .addOnFailureListener(e -> {
                    Log.w("FAILURE", e.toString());
                });
    }
}
