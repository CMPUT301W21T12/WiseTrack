package com.faanggang.wisetrack;

public class Experiment {
    String name;
    String description;
    String region;
    int minTrials;

    public Experiment(String name, String description, String region, int minTrials) {
        this.name = name;
        this.description = description;
        this.region = region;
        this.minTrials = minTrials;
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
}
