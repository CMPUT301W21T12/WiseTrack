package com.faanggang.wisetrack.search;

import com.faanggang.wisetrack.Experiment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SearchManager {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ArrayList<Experiment> searchForQuery(String query) {

        return new ArrayList<Experiment>();
    }
}
