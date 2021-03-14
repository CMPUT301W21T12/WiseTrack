package com.faanggang.wisetrack.statsManager;

public class StatReport {
     private float mean;
     private float median;
     private double stdev; // check this idk if theres gonna be a type error
     private float[] quartiles;
     // Could add mode aswell  for larger trials.
     public StatReport(float mean, float median, double stdev, float[] quartiles) {
          this.mean = mean;
          this.median = median;
          this.stdev = stdev;
          this.quartiles = quartiles;

     }

     /**
      * Calcualtes Mean of trials.
      * add all elements of the array together then divide by its length
      * @param trialTests
      * @return
      */
     public float calculateMean(float[] trialTests) {
          for (int index = 0; index < trialTests.length; index ++ ) {
               mean += trialTests[index];
          }
          mean = mean/trialTests.length;
          return mean;
     }

     /**
      * Calculates median of trials
      * sort the array (to be implemented later
      * mod the length
      * case 1 (odd) : round down and take that as index to be the median
      * case 2 (even): take the middle index and that index + 1 elements divided by 2
      * trial CHECK DONT DO ARRAYS LESS THAN 2
      * @param trialTests
      * @return
      */
     public float calculateMedian(float[] trialTests) {
          //sort array
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
      * @return
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
      * to be implemented later
      * @param numbers
      * @return
      */
     public float[] calculateQuartiles(float[] numbers) {

          return quartiles;
     }
}
