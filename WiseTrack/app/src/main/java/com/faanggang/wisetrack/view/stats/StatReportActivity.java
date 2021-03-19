package com.faanggang.wisetrack.view.stats;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.StatManager;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * Allows the view of an experiment's statistical data
 * (i.e Mean, Median, Standard deviation and Quartile) ... could potentially add Mode and IQR.
 */
public class StatReportActivity extends AppCompatActivity {
    private StatManager statManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String expID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_report_screen);
        statManager.generateStatReport();


        TextView exprName = (TextView) findViewById(R.id.stats_report_experiment_name); // Extraced from experiment.name from firebase
        TextView statMean = (TextView) findViewById(R.id.stats_mean2);
        TextView statMedian = (TextView) findViewById(R.id.stats_median2);
        TextView statStdev = (TextView) findViewById(R.id.stats_stdev2);
        TextView statQuartile = (TextView) findViewById(R.id.stat_quartiles2);


        /**
         * Currently only views experiment's name would need to extract other values later.
         */
        /**
         *

        db.collection("Experiments").document(expID).get()
                .addOnCompleteListener(task ->{
                    if (task.isSuccessful()){
                        DocumentSnapshot docSnap = task.getResult();
                        //exprName.setText(docSnap.getString("name"));
                        // would need to extract trial content from database as well
                    }
                })
        ;
         */



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