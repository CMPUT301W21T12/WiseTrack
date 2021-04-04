package com.faanggang.wisetrack.view.stats;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.StatManager;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;


/**
 * Allows the view of an experiment's statistical data
 * (i.e Mean, Median, Standard deviation and Quartile) ... could potentially add Mode and IQR.
 */
public class StatReportActivity extends AppCompatActivity {
    private StatManager statManager = new StatManager();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String expID;
    private ExperimentManager experimentManager;

    private TextView exprName; // Extracted from experiment.name from firebase
    private TextView statMean;
    private TextView statMedian;
    private TextView statStdev;
    private TextView statQuartile;
    private TextView statMinimum;
    private TextView statMaximum;

    private List<Float> trialData = new ArrayList<Float>();// why is it still saying "Attempt to invoke virtual method 'void com.faanggang.wisetrack.controllers.StatManager.generateStatReport(java.util.List)' on a null object reference" :(
    private List<Float> testData = new ArrayList<Float>();// tester array to be put into  an actual test later (42f, 3f, 4f, 7f, 18f, 21f, 26f, 44f, 69f, 10f

    /**
     * 42f , 3f, 4f, 7f, 18f, 21f, 26f, 44f, 69f, 10f IDK WHY EVERYHING POINTS TO NULL :(
     * Initialize all private names
     * Gather data from firebase
     * Set text to all TextViews
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_report_screen);
        experimentManager = new ExperimentManager();
        exprName = findViewById(R.id.stats_report_experiment_name); // Extracted from experiment.name from firebase
        statMean =  findViewById(R.id.stats_mean2);
        statMedian =  findViewById(R.id.stats_median2);
        statStdev = findViewById(R.id.stats_stdev2);
        statQuartile =  findViewById(R.id.stat_quartiles2);
        statMinimum = findViewById(R.id.stat_minimum2);
        statMaximum = findViewById(R.id.stat_maximum2);
        experimentManager = new ExperimentManager();
        expID = getIntent().getStringExtra("EXP_ID");

        trialDataQuery();
        statManager.generateStatReport(trialData);
        setTextView();
    }

    /**
     * Query for a trial's experiment data and name
     * Only queries for trial name would need to get data later.
     * The database collection is saying null
     */
    public void trialDataQuery() {
        experimentManager.getExperimentInfo(expID, task -> {
            DocumentSnapshot docSnap = task.getResult();
            exprName.setText(docSnap.getString("name"));
        });

    }

    /**
     * Set Text to the TextView's of:
     *  Mean
     *  Median
     *  Standard Deviation
     *  Quartiles
     */
    public void setTextView() {
        statMinimum.setText(String.valueOf(statManager.getMin()));
        statMaximum.setText(String.valueOf(statManager.getMax()));
        statMean.setText(String.valueOf(statManager.getMean()));
        statMedian.setText(String.valueOf(statManager.getMedian()));
        statStdev.setText(String.valueOf(statManager.getStdev()));

        String quartileString = "";
        List<Float> quartile = statManager.getQuartiles();
        for (int index =0 ; index < 3; index++ ){ // quartiles are only from q1 to q3
            if (index < 3) {
                quartileString += quartile.get(index) + ", ";
            } else {
                quartileString += quartile.get(index);
            }
        }
        statQuartile.setText(quartileString);
    }

}