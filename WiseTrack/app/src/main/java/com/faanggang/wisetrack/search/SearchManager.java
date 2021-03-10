package com.faanggang.wisetrack.search;

import android.util.Log;

import androidx.annotation.NonNull;

import com.faanggang.wisetrack.Experiment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.CollectionReference;
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
        void onSearchSuccess(List results);
    }

    public SearchManager(Searcher searcher) {
        db = FirebaseFirestore.getInstance();
        this.searcher = searcher;
    }

    public ArrayList<Experiment> searchForQuery(String query) {

        CollectionReference experiments = db.collection("Experiments");
        ArrayList<Experiment> results;
        Log.w("SEARCH", "searchForQuery");

        ArrayList<String> queryKeywords = new ArrayList<>();
        queryKeywords.addAll(Arrays.asList(query.split(" ")));

        if (queryKeywords.size() > 10) {
            queryKeywords.subList(0, 10);
        }
        Query searchQuery = experiments.orderBy("date").whereArrayContainsAny("keywords",
                queryKeywords);
        searchQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    searcher.onSearchSuccess(Arrays.asList(task.getResult().getDocuments().toArray()));
                } else {
                    Log.w("FAILURE", task.getException().toString());
                }
            }
        });

        return new ArrayList<Experiment>();
    }
}
