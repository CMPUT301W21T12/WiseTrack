package com.faanggang.wisetrack.model.executeTrial;

import android.location.Location;

import java.util.Date;

public class MeasurementTrial extends Trial {
    private float measurement;

    /**
     * @param measurement     : the trial result
     * @param trialGeolocation : string input of location for which current trial was conducted
     * @param uID              : user id of the trial conductor
     * @param date
     */
    public MeasurementTrial(float measurement, Location trialGeolocation, String uID, Date date) {
        super(trialGeolocation, uID, date);
        this.measurement = measurement;
    }

    public float getMeasurement() {
        return measurement;
    }

    public void setMeasurement(float measurement) {
        this.measurement = measurement;
    }
}
