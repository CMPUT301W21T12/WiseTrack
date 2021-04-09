package com.faanggang.wisetrack.model.stats;


import android.provider.ContactsContract;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Handle the creation of the histogram values
 */
public class StatHistogram {
    private List<Float> values = new ArrayList<>();

    public StatHistogram () {

    }

    /**
     * Creates a dataPointList for the total resultant count.
     * Get the total value of all the counts
     * X: One bar for total count
     * Y: Total Count value
     * @param trialData
     */
    public List<DataPoint> drawHistogramCount(List<Float> trialData) {
        List<DataPoint> dataPointList = new ArrayList<>();
        DataPoint dataPoint = new DataPoint(1,trialData.size()); // see how this goes when you play with it.

        dataPointList.add(dataPoint);

        return dataPointList;

    }
    /**
     * Creates a dataPointList of the successes and failures
     * X1: Successes
     * X2: Failures
     * Y: Value of each X(n)
     * @param trialData
     */
    public List<DataPoint>  drawHistogramBinomial(List<Float> trialData) {
        double success,failure;
        success = 0;
        failure = 0;
        List<DataPoint> dataPointList = new ArrayList<>();

        for (int y = 0; y < trialData.size(); y++) {
            if (trialData.get(y) == 1) { // success
                success += 1;
            } else { // failure
                failure += 1;
            }
        }
        DataPoint successPoint = new DataPoint(0,success);
        DataPoint failurePoint = new DataPoint( 1,failure);
        dataPointList.add(successPoint);
        dataPointList.add(failurePoint);

        return dataPointList;
    }
    /**
     * Creates a dataPoint list of occurrences of certain counts.
     * X: A resultant count value
     * Y: # of appearances of respective x value
     * @param trialData
     */
    public List<DataPoint>  drawHistogramNNIC(List<Float> trialData) {
        List<DataPoint> dataPointList = new ArrayList<>();
        Log.i("results log inner NNIC", trialData.toString());
        DataPoint dataPoint = new DataPoint(0,0);
        for (int j = 0; j < calculateMax(trialData)+1; j++) {
            int totalCount = 0;
            for (int k = 0; k < trialData.size(); k++) {
                if (j == trialData.get(k)) {
                    totalCount+= 1;
                }

            }
            dataPoint = new DataPoint(j,totalCount);
            if (dataPoint.getY() != 0) { // only add dataPoints with values innit.
                dataPointList.add(dataPoint);
            }
        }
        return dataPointList;
    }

    /**
     * Grabs the maximum value from experiment trials
     * @param trialTest
     * @return Largest result of an experiment
     */
    public float calculateMax(List<Float> trialTest) {
        Collections.sort(trialTest);
        float maximum = trialTest.get(trialTest.size()-1);
        return maximum;
    }

    /**
     * Creates a dataPointList of occurrences of measurements with a corrective rate of +/-~1
     * X: Measurement value
     * Y: Total occurence of a measurement within the x to x+1 from trial results.
     * @param trialData
     */
    public List<DataPoint>  drawHistogramMeasurement(List<Float> trialData) { // similar to NNIC just correct the range of floating point values.
        List<DataPoint> dataPointList = new ArrayList<>();
        DataPoint dataPoint = new DataPoint(0,0);
        int upperBound = 1;
        int lowerBound = 0;
        for (int j = 0; j < calculateMax(trialData)+1; j++) {
            int totalCount = 0;
            upperBound = j+1;
            lowerBound = j;
            for (int k = 0; k < trialData.size(); k++) {
                if (trialData.get(k) >= lowerBound && trialData.get(k) < upperBound) {
                    totalCount += 1;
                }

            }
            dataPoint = new DataPoint(lowerBound, totalCount);
            if (dataPoint.getY() != 0) { // only add non-zero values
                dataPointList.add(dataPoint);
            }

        }

        return dataPointList;
    }



}
