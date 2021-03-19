package com.faanggang.wisetrack.executeTrial;

import java.util.Date;

public class MeasurementTrial extends Trial {
    private float measurement;

    /**
     * @param measurement     : the trial result
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param trialDescription : an optional field, default set to empty string
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public MeasurementTrial(float measurement, String trialGeolocation, String trialDescription, String uID, Date date) {
        super(trialGeolocation, trialDescription, uID, date);
        this.measurement = measurement;
    }

    public float getMeasurement() {
        return measurement;
    }

    public void setMeasurement(float measurement) {
        this.measurement = measurement;
    }
}
