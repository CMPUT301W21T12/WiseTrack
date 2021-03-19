package com.faanggang.wisetrack.executeTrial;

import android.widget.Toast;

import java.util.Date;

public class CountTrial extends Trial{
    private int count;
    private int trialType;
    /**
     * @param count: current trial test result - count type or non-negative
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param trialDescription : an optional field, default set to empty string
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public CountTrial(int count, int trialType, String trialGeolocation, String trialDescription, String uID, Date date) {
        super(trialGeolocation, trialDescription, uID, date);
        this.trialType = trialType;

        if (trialType == 0) {
            this.count = count;  // count type
        } else if (trialType == 2) {
            // non-negative count type
            if (count < 0) {
                throw new IllegalArgumentException("Trial type must be non-negative integer count.");
            } else {
                this.count = count;
            }
        }
    }

    // dummy constructor for testing
    public CountTrial(int count, String trialGeolocation, String trialDescription, String uID, Date date) {
        super(trialGeolocation, trialDescription, uID, date);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
