package com.faanggang.wisetrack.model.stats;

import android.util.Log;

import com.google.firebase.Timestamp;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class StatPlot {
    private List<Float> values;

    public StatPlot() {

    }

    /**
     * Converts timestamp lists into date lists
     *
     * @param timeStamp
     * @return dateList
     */
    public List<Date> timeStampToDate(List<Timestamp> timeStamp) {
        List<Date> dateList = new ArrayList<>();

        for (int x = 0; x < timeStamp.size(); x++) {
            dateList.add(timeStamp.get(x).toDate());
        }
        return dateList;
    }

    /**
     * Creates a dataPointList for the total resultant count.
     * X: Time range
     * Y: Count value during the time range
     * 24 hour interval
     *
     * @param trialData
     */
    public List<DataPoint> drawPlotCount(List<Float> trialData, List<Timestamp> timeStamp) {

        List<DataPoint> dataPointList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();
        dateList = timeStampToDate(timeStamp);

        for (int j = 0; j < dateList.size(); j++) {
            int currentCount = 0;
            for (int k = 0; k < dateList.size(); k++) {
                if (dateList.get(k) == dateList.get(j)) {
                    currentCount += 1;
                }

            }
            DataPoint dataPoint = new DataPoint(dateList.get(j), currentCount);
            if (currentCount != 0) {
                dataPointList.add(dataPoint);
            }

        }
        return dataPointList;

    }

    /**
     * Creates a dataPointList of the successes
     * X: Time range
     * Y: Successes for that day
     * 24 hour interval
     *
     * @param trialData
     */
    public List<DataPoint> drawPlotBinomial(List<Float> trialData, List<Timestamp> timeStamp) {

        List<DataPoint> dataPointList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();
        dateList = timeStampToDate(timeStamp);

        int success = 0;
        for (int x = 0; x < dateList.size(); x++) {
            for (int y = 0; y < dateList.size(); y++ ) {
                if (dateList.get(x) == dateList.get(y)) {// same day
                    if(trialData.get(x) == 1) { // success
                        success += 1;
                    }
                }
            }
            DataPoint successPoint = new DataPoint(dateList.get(x), success);
            if (success != 0) {
                dataPointList.add(successPoint);
            }
        }
        return dataPointList;
    }

    /**
     * Creates a dataPoint list of mean value counts.
     * X: Time range
     * Y: Mean value for one day
     * 24 hour interval
     * @param trialData
     */
    public List<DataPoint>  drawPlotNNIC(List<Float> trialData, List<Timestamp> timeStamp) {
        List<DataPoint> dataPointList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();
        dateList = timeStampToDate(timeStamp);

        for (int x = 0; x < dateList.size() ; x++) {
            float mean = 0f;
            int instance = 0;
            for (int y = 0; y < dateList.size(); y++) {
                if (dateList.get(x) == dateList.get(y) ) {
                    mean += trialData.get(x);
                    instance += 1;
                }
            }
            if (instance > 0) {
                mean = mean / instance;
                DataPoint dataPoint = new DataPoint(dateList.get(x),mean);
                if (mean != 0) {
                    dataPointList.add(dataPoint);
                }
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
     * X: Time range
     * Y: Total occurence of a measurement within the x to x+1 from trial results.
     * @param trialData
     */
    public List<DataPoint>  drawPlotMeasurement(List<Float> trialData, List<Timestamp> timeStamp) { // similar to NNIC just correct the range of floating point values.
        List<DataPoint> dataPointList = new ArrayList<>();
        List<Date> dateList = new ArrayList<>();
        dateList = timeStampToDate(timeStamp);

        for (int x = 0; x < dateList.size() ; x++) {
            float mean = 0f;
            int instance = 0;
            for (int y = 0; y < dateList.size(); y++) {
                if (dateList.get(x) == dateList.get(y) ) {
                    mean += trialData.get(x);
                    instance += 1;
                }

            }
            if (instance > 0) {
                mean = mean / instance;
                DataPoint dataPoint = new DataPoint(dateList.get(x),mean);
                if (mean != 0) {
                    dataPointList.add(dataPoint);
                }
            }
        }
        return dataPointList;
    }


}
