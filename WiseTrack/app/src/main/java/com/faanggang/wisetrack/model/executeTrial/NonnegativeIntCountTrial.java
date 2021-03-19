package com.faanggang.wisetrack.model.executeTrial;

import java.util.Date;

public class NonnegativeIntCountTrial extends Trial{
    private int nonnegCount;

    /**
     * @param count            : current trial test result
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param trialDescription : an optional field, default set to empty string
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public NonnegativeIntCountTrial(int count, String trialGeolocation, String trialDescription, String uID, Date date) {
        super(trialGeolocation, trialDescription, uID, date);

        if (count < 0) {
            throw new IllegalArgumentException("Current trial type only accepts non-negative integer count.");
        } else {
            nonnegCount = count;
        }
    }

    public int getNonnegCount() {
        return nonnegCount;
    }

    public void setNonnegCount(int nonnegCount) {
        this.nonnegCount = nonnegCount;
    }
}
