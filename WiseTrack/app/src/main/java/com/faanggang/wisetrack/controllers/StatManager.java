package com.faanggang.wisetrack.controllers;

import android.util.Log;

import com.faanggang.wisetrack.model.stats.StatHistogram;
import com.faanggang.wisetrack.model.stats.StatPlot;
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
    public StatReport currentTrialReport = new StatReport();
    private StatHistogram currentTrialHistogram;
    private StatPlot currentTrialPlot;
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
            Log.i("Stat Manager",
                    "Max " + String.valueOf(currentTrialReport.getMaximum()) +
                            "Min " + String.valueOf(currentTrialReport.getMinimum()) +
                            "QTR " + String.valueOf(currentTrialReport.getQuartiles()) +
                            "Mean " + String.valueOf(currentTrialReport.getMean()) +
                            "Median" + String.valueOf(currentTrialReport.getMedian()) );
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


    public void generateStatPlot(List<Float> trialData, int trialType) {// plots overtime * change return
        switch (trialType){
            case 0: // count
                currentTrialPlot.drawPlot();
                break;
            case 1: // binomial
                currentTrialPlot.drawPlot();
                break;
            case 2: // NNIC
                currentTrialPlot.drawPlot();
                break;
            case 3: // Measurement
                currentTrialPlot.drawPlot();
                break;
            default:
                Log.w("STSManager", "Plot: Error Trial Type");


        }
    }

    public void generateStatHistogram(List<Float> trialData, int trialType) { // theres a library somewhere * change return
        switch (trialType){
            case 0: // count
                currentTrialHistogram.drawHistogramCount();
                break;
            case 1: // binomial
                currentTrialHistogram.drawHistogramBinomial();
                break;
            case 2: // NNIC
                currentTrialHistogram.drawHistogramNNIC();
                break;

            case 3: // Measurement
                currentTrialHistogram.drawHistogramMeasurement();
                break;
            default:
                Log.w("STSManager", "Histogram: Error Trial Type");
        }





    }


}



