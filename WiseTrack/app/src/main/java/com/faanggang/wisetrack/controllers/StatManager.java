package com.faanggang.wisetrack.controllers;

import com.faanggang.wisetrack.model.stats.StatReport;

/**
 * Manage and handle all statistic related methods for experiments
 * To be used in all activity files
 * Important things to keep in mind:
 * Trial type is determined by an int via "trialType"
 * 0 - Count
 * 1 - Binomial
 * 2 - Non - Negative
 * 3 - Measurements
 */
public class StatManager {
    public StatReport currentTrialReport = new StatReport(0,0,0, new float[]{0, 0, 0});
    //private StatHistogram currentTrialHistogram;
    //private StatPlot currentTrialPlot;
    public StatManager () {

    }
    /**
     * Calculate the following:
     * Median , Mean, Std. Deviation and quartiles
     */
    public void generateStatReport() {
        float[] trialData = new float[]{42,3,4,7,18,21,26,44,69,10}; // Test array

        // grab trial data from trial as a float array
        currentTrialReport.setMean(currentTrialReport.calculateMean(trialData));
        currentTrialReport.setMedian(currentTrialReport.calculateMedian(trialData));
        currentTrialReport.setStdev(currentTrialReport.calculateStdev(trialData));
        currentTrialReport.setQuartiles(currentTrialReport.calculateQuartiles(trialData));
        currentTrialReport.setInterquartileRange(currentTrialReport.calculateInterquartileRange(trialData));



    }
    public void displayStatReport() {// What is this for ?
        /**
         * Displays the following (needs to change based on trial to get more digestable information)
         * Default is : Median , Mean , Standard Deviation and quartiles
         */
    }

    public void generateStatPlot() {// plots overtime * change return

    }

    public void displayStatPlot() {

    }
    public void generateHistorgram() { // theres a library somewhere * change return

    }
    public void displayHistorgram() {

    }

}



