package com.faanggang.wisetrack.model.executeTrial;

import java.util.Date;

public class BinomialTrial extends Trial{
    private int trialResult;

    /**
     * @param trialResult   : Integer indicating trial result - "success: 1"/"failure: 0"/"Default: -1"
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public BinomialTrial(int trialResult, String trialGeolocation, String uID, Date date) {
        super(trialGeolocation, uID, date);
        this.trialResult = trialResult;
    }

    public int getTrialResult() {
        return trialResult;
    }

    public void setSuccess(boolean success) {
        this.trialResult = trialResult;
    }
}
