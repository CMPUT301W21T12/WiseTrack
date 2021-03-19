package com.faanggang.wisetrack.model.executeTrial;

import java.util.Date;

public abstract class Trial {
    private String trialGeolocation;
    private String trialDescription = "";
    private String experimenterID;
    private Date datetime;
    private String trialID;  // trial document ID in firebase

    /**
     * @param trialGeolocation: string input of location for which current trial was conducted
     * @param trialDescription: an optional field, default set to empty string
     * @param uID: user id of the trial conductor
     * @param date
     */
    public Trial(String trialGeolocation, String trialDescription, String uID, Date date) {
        this.trialGeolocation = trialGeolocation;
        this.trialDescription = trialDescription;
        this.experimenterID = uID;
        this.datetime = date;
    }

    public String getTrialGeolocation() {
        return trialGeolocation;
    }

    public void setTrialGeolocation(String trialGeolocation) {
        this.trialGeolocation = trialGeolocation;
    }

    public String getTrialDescription() {
        return trialDescription;
    }

    public void setTrialDescription(String trialDescription) {
        this.trialDescription = trialDescription;
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
