package com.faanggang.wisetrack.model.executeTrial;

import android.location.Location;

import java.util.Date;

public class BinomialTrial extends Trial{
    private double trialResult;

    /**
     * @param trialResult   : Integer indicating trial result - "success: 1"/"failure: 0"/"Default: -1"
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public BinomialTrial(double trialResult, Location trialGeolocation, String uID, Date date, int trialType) {
        super(trialGeolocation, uID, date, trialResult, trialType);
        //this.trialResult = trialResult;
    }

    public double getTrialResult() {
        return trialResult;
    }

    public void setSuccess(boolean success) {
        this.trialResult = trialResult;
    }
}
