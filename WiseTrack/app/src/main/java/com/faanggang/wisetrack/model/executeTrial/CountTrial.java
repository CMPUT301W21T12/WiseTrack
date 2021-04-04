package com.faanggang.wisetrack.model.executeTrial;

import java.util.Date;

public class CountTrial extends Trial{
    private int count;
    /**
     * @param count: current trial test result - count type or non-negative
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public CountTrial(int count, String trialGeolocation, String uID, Date date) {
        super(trialGeolocation, uID, date);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
