package com.faanggang.wisetrack;

import java.util.Date;

public class Experiment {
    private String name;
    private String description;
    private String region;
    private int minTrials;
    private int crowdSource;
    private boolean geolocation;
    private Date date;
    private String ownerID;

    public Experiment(String name, String description, String region,
                      int minTrials, int crowdSource, boolean geolocation, Date date,
                      String ownerID) {
        this.name = name;
        this.description = description;
        this.region = region;
        this.minTrials = minTrials;
        this.crowdSource = crowdSource;
        this.geolocation = geolocation;
        this.date = date;
        this.ownerID = ownerID;
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

    public int getCrowdSource() {
        return crowdSource;
    }

    public void setCrowdSource(int crowdSource) {
        this.crowdSource = crowdSource;
    }

    public boolean getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(boolean geolocation) {
        this.geolocation = geolocation;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }
}
