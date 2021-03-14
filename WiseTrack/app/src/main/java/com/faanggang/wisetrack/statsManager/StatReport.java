package com.faanggang.wisetrack.statsManager;

public class StatReport {
     private float mean;
     private float median;
     private float stdev;
     private float[] quartiles;

     public StatReport(float mean, float median, float stdev, float[] quartiles) {
          this.mean = mean;
          this.median = median;
          this.stdev = stdev;
          this.quartiles = quartiles;

     }

     public void calculate() {
          // do calculations here.
     }
}
