package com.faanggang.wisetrack.statsManager;

/**
 * Manage and handle all statistic related methods for experiments
 *
 * Important things to keep in mind:
 * Trial type is determined by an int via "crowdSource"
 * 0  -Count
 * 1 - Binomial
 * 2 - Non - Negative
 * 3 - Measurements
 */
public class StatManager {
    public StatManager () {

    }
    public void generateStatReport() { // change return
        /**
         * Calculate the following:
         * Median , Mean, Std. Deviation and quartiles
         */
    }
    public void displayStatReport() {
        /**
         * Displays the following (needs to change based on trial to get more digestable information
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



