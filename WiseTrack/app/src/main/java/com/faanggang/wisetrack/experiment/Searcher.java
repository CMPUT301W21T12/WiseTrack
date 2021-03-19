package com.faanggang.wisetrack.experiment;

import java.util.ArrayList;
/**
 * This is an interface that is implemented by Activities that forces implementing activities
 * to implement a method to be called upon a successful search.
 */
public interface Searcher {
    void onSearchSuccess (ArrayList<Experiment> results);
}
