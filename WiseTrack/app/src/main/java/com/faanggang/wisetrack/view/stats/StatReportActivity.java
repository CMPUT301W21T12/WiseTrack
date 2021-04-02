package com.faanggang.wisetrack.view.stats;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.StatManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Allows the view of an experiment's statistical data
 * (i.e Mean, Median, Standard deviation and Quartile) ... could potentially add Mode and IQR.
 */
public class StatReportActivity extends AppCompatActivity {
    private StatManager statManager;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String expID;
    private ExperimentManager experimentManager;

    private TextView exprName; // Extracted from experiment.name from firebase
    private TextView statMean;
    private TextView statMedian;
    private TextView statStdev;
    private TextView statQuartile;

    private List<Float> trialData = new ArrayList<Float>();// why is it still saying "Attempt to invoke virtual method 'void com.faanggang.wisetrack.controllers.StatManager.generateStatReport(java.util.List)' on a null object reference" :(
    private List<Float> testData = new ArrayList<Float>();// tester array to be put into  an actual test later (42f, 3f, 4f, 7f, 18f, 21f, 26f, 44f, 69f, 10f

    /**
     * Initialize all private names
     * Gather data from firebase
     * Set text to all TextViews
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_report_screen);
        exprName = findViewById(R.id.stats_report_experiment_name); // Extracted from experiment.name from firebase
        statMean =  findViewById(R.id.stats_mean2);
        statMedian =  findViewById(R.id.stats_median2);
        statStdev = findViewById(R.id.stats_stdev2);
        statQuartile =  findViewById(R.id.stat_quartiles2);

        experimentManager = new ExperimentManager();
        expID = getIntent().getStringExtra("EXP_ID");

        trialDataQuery();


        tempTest();
        trialData.addAll(testData);

        statManager.generateStatReport(trialData);
        setTextView();
    }

    /**
     * Query for a trial's experiment data and name
     * Only queries for trial name would need to get data later.
     */
    public void trialDataQuery() {
        /*
        experimentManager.getExperimentInfo(expID, task->{
            DocumentSnapshot docSnap = task.getResult();
            exprName.setText(docSnap.getString("name"));
            // get trial data later.
            });
        */
        exprName.setText("Trial Name Here");

    }

    /**
     * Set Text to the TextView's of:
     *  Mean
     *  Median
     *  Standard Deviation
     *  Quartiles
     */
    public void setTextView() {

        statMean.setText(String.valueOf(statManager.currentTrialReport.getMean()));
        statMedian.setText(String.valueOf(statManager.currentTrialReport.getMedian()));
        statStdev.setText(String.valueOf(statManager.currentTrialReport.getStdev()));

        String quartileString = "";
        List<Float> quartile = statManager.currentTrialReport.getQuartiles();
        for (int index =0 ; index < 3; index++ ){ // quartiles are only from q1 to q3
            if (index < 3) {
                quartileString += quartile.get(index) + ", ";
            } else {
                quartileString += quartile.get(index);
            }
        }
        statQuartile.setText(quartileString);
    }

    /**
     *  42f , 3f, 4f, 7f, 18f, 21f, 26f, 44f, 69f, 10f
     */
    public void tempTest() {
        testData.add(42f);
        testData.add(3f);
        testData.add(4f);
        testData.add(7f);
        testData.add(18f);
        testData.add(21f);
        testData.add(26f);
        testData.add(44f);
        testData.add(69f);
        testData.add(10f);



    }
}