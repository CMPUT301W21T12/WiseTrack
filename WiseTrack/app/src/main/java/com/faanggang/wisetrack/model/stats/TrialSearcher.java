package com.faanggang.wisetrack.model.stats;

import com.faanggang.wisetrack.model.executeTrial.Trial;
import java.util.ArrayList;


public interface TrialSearcher {
    void onQuerySuccess(ArrayList<Trial> results);
}
