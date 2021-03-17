package com.faanggang.wisetrack.statsManager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;


/**
 * Allows the view of an experiment's statistical data
 * (i.e Mean, Median, Standard deviation and Quartile) ... could potentially add Mode and IQR.
 */
public class StatReportActivity extends AppCompatActivity {
    private StatManager statManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_report_screen);
        statManager.generateStatReport();

        TextView trialName = (TextView) findViewById(R.id.trial_name); // where do I grab this.
        TextView statMean = (TextView) findViewById(R.id.stats_mean2);
        TextView statMedian = (TextView) findViewById(R.id.stats_median2);
        TextView statStdev = (TextView) findViewById(R.id.stats_stdev2);
        TextView statQuartile = (TextView) findViewById(R.id.stat_quartiles2);

        statMean.setText(String.valueOf(statManager.currentTrialReport.getMean()));
        statMedian.setText(String.valueOf(statManager.currentTrialReport.getMedian()));
        statStdev.setText(String.valueOf(statManager.currentTrialReport.getStdev()));

        String quartileString = "";
        float[] quartile = statManager.currentTrialReport.getQuartiles();
        for (int index =0 ; index < 3; index++ ){ // quartiles are only from q1 to q3
            quartileString += quartile[index] + ", ";
        }
        statQuartile.setText(quartileString);


    }
}