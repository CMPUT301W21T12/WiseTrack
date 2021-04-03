package com.faanggang.wisetrack.controllers;

import android.util.Log;

import com.faanggang.wisetrack.model.stats.StatReport;

import java.util.Arrays;
import java.util.List;

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

/**
 * Testing
 * value check
 * hopefully inner values
 */

// add test for values
public class StatManager {
    public StatReport currentTrialReport = new StatReport(0,100,50,50,39.52, Arrays.asList(12.5f,50f,87.5f));
    //private StatHistogram currentTrialHistogram;
    //private StatPlot currentTrialPlot;
    public StatManager () {

    }
    /**
     * Calculate the following:
     * Median , Mean, Std. Deviation and quartiles
     * @param trialData from trial as a float array
     */
    public void generateStatReport(List<Float> trialData) {
        if (trialData.size() > 1) {
            currentTrialReport.setMean(currentTrialReport.calculateMean(trialData));
            currentTrialReport.setMedian(currentTrialReport.calculateMedian(trialData));
            currentTrialReport.setStdev(currentTrialReport.calculateStdev(trialData));
            currentTrialReport.setQuartiles(currentTrialReport.calculateQuartiles(trialData));
            currentTrialReport.setInterquartileRange(currentTrialReport.calculateInterquartileRange(trialData));
            currentTrialReport.setMaximum(currentTrialReport.calculateMax(trialData));
            currentTrialReport.setMinimum(currentTrialReport.calculateMin(trialData));
        }


    }

    /**
     * Following methods get the min,max,median,std. dev and quartiles for ease of use for GUI side
     */
    public float getMin() {return currentTrialReport.getMinimum();}
    public float getMax() {return currentTrialReport.getMaximum();}
    public float getMean() { return currentTrialReport.getMean();}
    public float getMedian() { return currentTrialReport.getMedian();}
    public double getStdev() { return currentTrialReport.getStdev();}
    public List<Float> getQuartiles() {return currentTrialReport.getQuartiles();}
    public float getIQR() {return currentTrialReport.getInterquartileRange();}


    public void generateStatPlot() {// plots overtime * change return

    }

    public void generateHistorgram() { // theres a library somewhere * change return

    }


}



