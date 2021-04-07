package com.faanggang.wisetrack.model.executeTrial;

import android.location.Location;

import java.util.Date;

public abstract class Trial {
    private Location trialGeolocation;
    private String experimenterID;
    private Date datetime;
    private String trialID;  // trial document ID in firebase

    /**
     * @param trialGeolocation: string input of location for which current trial was conducted
     * @param uID: user id of the trial conductor
     * @param date
     */
    public Trial(Location trialGeolocation, String uID, Date date) {
        this.trialGeolocation = trialGeolocation;
        this.experimenterID = uID;
        this.datetime = date;
    }

    public Location getTrialGeolocation() {
        return trialGeolocation;
    }

    public void setTrialGeolocation(Location trialGeolocation) {
        this.trialGeolocation = trialGeolocation;
    }

    public String getExperimenterID() {
        return experimenterID;
    }

    public void setExperimenterID(String uID) {
        this.experimenterID = uID;
    }

    public String getTrialID() {
        return trialID;
    }

    public void setTrialID(String trialID) {
        this.trialID = trialID;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
