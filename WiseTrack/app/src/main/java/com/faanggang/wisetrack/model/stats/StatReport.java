package com.faanggang.wisetrack.model.stats;

import java.util.Arrays;

/**
 * Java Documentation for arrays : https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html
 */
public class StatReport {
     private float mean;
     private float median;
     private double stdev; // check this idk if theres gonna be a type error
     private float[] quartiles;
     private float interquartileRange;  // implemented but not put in cause its not asked for.
     private float mode; //
     public StatReport(float mean, float median, double stdev, float[] quartiles) {
          this.mean = mean;
          this.median = median;
          this.stdev = stdev;
          this.quartiles = quartiles;

     }
     public StatReport() {
          this.mean = 0;
          this.median = 0;
          this.stdev = 0;
          this.quartiles = new float[]{0,0,0};
     }


     /**
      * Calculates Mean of trials.
      * add all elements of the array together then divide by its length
      * @param trialTests
      * @return Mean
      */
     public float calculateMean(float[] trialTests) {
          for (int index = 0; index < trialTests.length; index ++ ) {
               mean += trialTests[index];
          }
          mean = mean/trialTests.length;


          return mean;
     }

     /**
      * Calculates Median of trials
      * sort the array (to be implemented later)
      * mod the length
      * case 1 (odd) : round down and take that as index to be the median
      * case 2 (even): take the middle index and that index + 1 elements divided by 2
      * trial CHECK DONT DO ARRAYS LESS THAN 2
      * @param trialTests
      * @return Median from an array of data
      */
     public float calculateMedian(float[] trialTests) {
          Arrays.sort(trialTests);
          int medianIndicator = trialTests.length%2;
          int middle = trialTests.length/2;
          if (medianIndicator == 0) { //even length
               median = (trialTests[middle] + trialTests[middle-1])/2; // CHANGE THIS THE ARRAY is 0 based
          } else if (medianIndicator == 1) { // odd length
               median = trialTests[middle]; // make this round down
          }

          return median;
     }


     /**
      * Calculates sample standard deviation.
      * Currently of double type might need to check it later
      *
      * @param trialTests
      * @return Standard deviation of a trial
      */
     public double calculateStdev(float[] trialTests) {
          mean = calculateMean(trialTests);
          double sum = 0;
          for (int index = 0 ; index < trialTests.length; index++) {
               sum += Math.pow((trialTests[index] - mean),2);
          }
          stdev = Math.sqrt(sum/(trialTests.length-1));


          return stdev;
     }

     /**
      * Calculates the quartiles (Q1 - Q3)  from a set of data
      * Q2 comes from the median.
      * Q1 and Q3 is found after a subset of the original array is broken up.
      * If the array is originally an even length then we can use the formula
      * Else we use the calculate median method via a subset of the original array.
      *
      * Array must be a length of 4 or greater for this stat to be  will put checks later
      * @param trialTests
      * @return Quartiles as an array of floats.
      */
     public float[] calculateQuartiles(float[] trialTests) {
          float quartileOne = 0;
          float quartileTwo = 0;
          float quartileThree = 0;
          Arrays.sort(trialTests);
          quartileTwo = calculateMedian(trialTests);
          if (trialTests.length % 2 == 0) { // Even length
               quartileOne = trialTests[(trialTests.length+1)/4];
               quartileThree = trialTests[3*(trialTests.length)/4];
          } else{ // Odd length
               int middleIndex = trialTests.length/2;
               float[] lowSubset = Arrays.copyOfRange(trialTests, 0, middleIndex);
               float[] highSubset = Arrays.copyOfRange(trialTests, middleIndex+1, trialTests.length);

               quartileOne = calculateMedian(lowSubset);
               quartileThree = calculateMedian(highSubset);
          }


          float [] quartiles = new float[]{ quartileOne, quartileTwo, quartileThree};


          return quartiles;
     }

     /**
      * THIS IS SUPPLEMENTARY AND NOT ASKED OF BY THE STUDY but could be useful in terms of stats
      * @returns IQR of a data set
      */
     public float calculateInterquartileRange(float[] trialTests) {
          float interquartileRange = 0;
          float[] quartiles = new float[3];

          quartiles = calculateQuartiles(trialTests);
          interquartileRange = quartiles[3] - quartiles[0];

          return interquartileRange;
     }

     public float getMean() {
          return mean;
     }

     public void setMean(float mean) {
          this.mean = mean;
     }

     public float getMedian() {
          return median;
     }

     public void setMedian(float median) {
          this.median = median;
     }

     public double getStdev() {
          return stdev;
     }

     public void setStdev(double stdev) {
          this.stdev = stdev;
     }

     public float[] getQuartiles() {
          return quartiles;
     }

     public void setQuartiles(float[] quartiles) {
          this.quartiles = quartiles;
     }

     public float getInterquartileRange() {
          return interquartileRange;
     }

     public void setInterquartileRange(float interquartileRange) {
          this.interquartileRange = interquartileRange;
     }

     public float getMode() {
          return mode;
     }

     public void setMode(float mode) {
          this.mode = mode;
     }
}
