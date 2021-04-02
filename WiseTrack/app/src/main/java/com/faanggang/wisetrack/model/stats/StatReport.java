package com.faanggang.wisetrack.model.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Java Documentation for arrays : https://docs.oracle.com/javase/6/docs/api/java/util/Arrays.html
 * Java Documentation for ArrayList: https://docs.oracle.com/javase/8/docs/api/java/util/ArrayList.html
 * Java Documentation for Lists: https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 */
public class StatReport {
     private float mean;
     private float median;
     private double stdev; // check this idk if theres gonna be a type error
     private List<Float> quartiles;
     private float interquartileRange;  // implemented but not put in cause its not asked for.
     private float mode; //
     public StatReport(float mean, float median, double stdev, List<Float> quartiles) {
          this.mean = mean;
          this.median = median;
          this.stdev = stdev;
          this.quartiles = quartiles;

     }
     public StatReport() {
          this.mean = 0;
          this.median = 0;
          this.stdev = 0;
          this.quartiles = new ArrayList<Float>();
     }


     /**
      * Calculates Mean of trials.
      * add all elements of the ArrayList together then divide by its length
      * @param trialTests
      * @return Mean
      */
     public float calculateMean(List<Float> trialTests) {
          for (int index = 0; index < trialTests.size(); index ++ ) {
               mean += trialTests.get(index);
          }
          mean = mean/trialTests.size();


          return mean;
     }

     /**
      * Calculates Median of trials
      * sort the array (to be implemented later)
      * mod the size
      * case 1 (odd) : round down and take that as index to be the median
      * case 2 (even): take the middle index and that index + 1 elements divided by 2
      * trial CHECK DONT DO LISTS LESS THAN 2
      * @param trialTests
      * @return Median from an array of data
      */
     public float calculateMedian(List<Float> trialTests) {
          Collections.sort(trialTests);
          int medianIndicator = trialTests.size()%2;
          int middle = trialTests.size()/2;
          if (medianIndicator == 0) { //even length
               median = (trialTests.get(middle) + trialTests.get(middle-1))/2; // CHANGE THIS THE ARRAY is 0 based
          } else if (medianIndicator == 1) { // odd length
               median = trialTests.get(middle); // make this round down
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
     public double calculateStdev(List<Float> trialTests) {
          mean = calculateMean(trialTests);
          double sum = 0;
          for (int index = 0 ; index < trialTests.size(); index++) {
               sum += Math.pow((trialTests.get(index) - mean),2);
          }
          stdev = Math.sqrt(sum/(trialTests.size()-1));


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
     public List<Float> calculateQuartiles(List<Float> trialTests) {
          float quartileOne = 0;
          float quartileTwo = 0;
          float quartileThree = 0;
          Collections.sort(trialTests);
          quartileTwo = calculateMedian(trialTests);
          if (trialTests.size() % 2 == 0) { // Even length
               quartileOne = trialTests.get((trialTests.size()+1)/4);
               quartileThree = trialTests.get(3*(trialTests.size())/4);
          } else{ // Odd length
               int middleIndex = trialTests.size()/2;
               // make a subset
               List<Float> lowSubset = trialTests.subList(0,middleIndex);
               List<Float> highSubset = trialTests.subList(middleIndex+1, trialTests.size());

               quartileOne = calculateMedian(lowSubset);
               quartileThree = calculateMedian(highSubset);
          }


          List<Float> quartiles = new ArrayList<Float>();
          quartiles.add(quartileOne);
          quartiles.add(quartileTwo);
          quartiles.add(quartileThree);

          return quartiles;
     }

     /**
      * THIS IS SUPPLEMENTARY AND NOT ASKED OF BY THE STUDY but could be useful in terms of stats
      * @returns IQR of a data set
      */
     public float calculateInterquartileRange(List<Float> trialTests) {
          float interquartileRange = 0;
          List<Float> quartiles = new ArrayList<Float>();

          quartiles = calculateQuartiles(trialTests);
          interquartileRange = quartiles.get(3) - quartiles.get(0);

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

     public List<Float> getQuartiles() {
          return quartiles;
     }

     public void setQuartiles(List<Float> quartiles) {
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
