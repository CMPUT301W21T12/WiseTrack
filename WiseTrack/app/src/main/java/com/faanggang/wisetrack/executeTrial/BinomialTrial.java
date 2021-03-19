package com.faanggang.wisetrack.executeTrial;

import java.util.Date;

public class BinomialTrial extends Trial{
    private int success;
    private int failure;

    /**
     * @param success          : interger value of total number of success
     * @param failure             : interger value of total number of fail
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param trialDescription : an optional field, default set to empty string
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public BinomialTrial(int success, int failure, String trialGeolocation, String trialDescription, String uID, Date date) {
        super(trialGeolocation, trialDescription, uID, date);

        this.success = success;
        this.failure = failure;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return failure;
    }

    public void setFail(int fail) {
        this.failure = fail;
    }
}
