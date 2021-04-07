package com.faanggang.wisetrack.model.executeTrial;

import java.util.Date;

public class MeasurementTrial extends Trial {
    private double measurement;

    /**
     * @param measurement     : the trial result
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public MeasurementTrial(double measurement, String trialGeolocation, String uID, Date date, int trialType) {
        super(trialGeolocation, uID, date, measurement, trialType);
        //this.measurement = measurement;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(float measurement) {
        this.measurement = measurement;
    }
}
