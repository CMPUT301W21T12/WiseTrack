package com.faanggang.wisetrack.model.executeTrial;

import android.location.Location;

import java.util.Date;

public class CountTrial extends Trial{
    private double trialResult;
    /**
     * @param trialResult: current trial test result - count type or non-negative
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public CountTrial(double trialResult, Location trialGeolocation, String uID, Date date, int trialType) {
        super(trialGeolocation, uID, date, trialResult, trialType);
        //this.trialResult = trialResult;
    }

    public double getTrialResult() {
        return trialResult;
    }

    public void setTrialResult(double trialResult) {
        this.trialResult = trialResult;
    }
}
