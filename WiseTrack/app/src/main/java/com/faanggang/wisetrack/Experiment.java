package com.faanggang.wisetrack;

import java.util.Date;

public class Experiment {
    private String name;
    private String description;
    private String region;
    private int minTrials;
    private int trialType;
    private boolean geolocation;
    private Date datetime;
    private String uID;
    private String expID;
    private boolean open;

    /**
     * @param name
     * @param description
     * @param region
     * @param minTrials: Minimum # of Trials. Must be greater than 1
     * @param trialType: 0 = Counts, 1 = Binomial, 2 = Non-negative integer counts,
     *                 3 = measurement trials
     * @param geolocation: true = geolocation required, false = geolocation optional
     * @param date
     * @param uID: userID of the owner (the user publishing the experiment)
     */

    public Experiment(String name, String description, String region,
                      int minTrials, int trialType, boolean geolocation, Date date,
                      String uID) {
        this.name = name;
        this.description = description;
        this.region = region;
        this.minTrials = minTrials;
        this.trialType = trialType;
        this.geolocation = geolocation;
        this.datetime = date;
        this.uID = uID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getMinTrials() {
        return minTrials;
    }

    public void setMinTrials(int minTrials) {
        this.minTrials = minTrials;
    }

    public int getTrialType() {
        return trialType;
    }

    public void setTrialType(int crowdSource) {
        this.trialType = crowdSource;
    }

    public boolean getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(boolean geolocation) {
        this.geolocation = geolocation;
    }

    public Date getDate() {
        return datetime;
    }

    public void setDate(Date date) {
        this.datetime = date;
    }

    public String getOwnerID() {
        return uID;
    }

    public void setOwnerID(String ownerID) {
        this.uID = uID;
    }

    public void setExpID(String id){ this.expID = id;}
    public String getExpID(){ return this.expID;}

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
