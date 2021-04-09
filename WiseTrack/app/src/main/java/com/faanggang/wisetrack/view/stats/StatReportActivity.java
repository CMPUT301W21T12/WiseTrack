package com.faanggang.wisetrack.view.stats;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.StatManager;
import com.faanggang.wisetrack.model.experiment.Searcher;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
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
    private Long trialType = -1L;  // integer indicator of trial type
    private TextView exprName; // Extracted from experiment.name from firebase
    private TextView statMean;
    private TextView statMedian;
    private TextView statStdev;
    private TextView statQuartile;
    private TextView statMinimum;
    private TextView statMaximum;

    private List<Float> trialData = new ArrayList<Float>();
    private List<Timestamp> trialTimeStamp = new ArrayList<Timestamp>();
    private List<Date> dateStamp = new ArrayList<>();
    /**
     * 42f , 3f, 4f, 7f, 18f, 21f, 26f, 44f, 69f, 10f
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
        expID = getIntent().getStringExtra("EXP_ID");

        experimentQuery();
        trialDataQuery(); // MAYBE FIX LATER IDK not cohesive
    }

    /**
     * Query for a Experiment's name and experiment type.
     */
    public void experimentQuery() {
        experimentManager.getExperimentInfo(expID, task->{
            DocumentSnapshot docSnap = task.getResult();
            exprName.setText(docSnap.getString("name"));
            trialType = docSnap.getLong("trialType");
        });
    }

    /**
     * Query for Trial's data
     * generates Stat report
     * Sets the textviews
     */
    public void trialDataQuery() {
        trialData.clear();
        Task<QuerySnapshot> task = db.getInstance().collection("Experiments").document(expID).collection("Trials").get();
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                float resultValue = 0f;
                Timestamp trialStamp = null;
                Date trialDate = null;
                for (DocumentSnapshot doc : documents) {
                    resultValue = doc.getLong("result").floatValue();
                    trialData.add(resultValue);
                }
                Log.i("TrialData", trialData.toString());
                statManager.generateStatReport(trialData);
                setTextView();
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Stats Report", "Trial Data NOT FOUND.");
            }
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
        Log.i("Stat Report", String.valueOf(statManager.getMax()) + String.valueOf(statManager.getMin()) + String.valueOf(statManager.getQuartiles()) + String.valueOf(statManager.getMean()) );
        statMinimum.setText(String.valueOf(statManager.getMin()));
        statMaximum.setText(String.valueOf(statManager.getMax()));
        statMean.setText(String.valueOf(statManager.getMean()));
        statMedian.setText(String.valueOf(statManager.getMedian()));
        statStdev.setText(String.valueOf(statManager.getStdev()));

        String quartileString = "";
        List<Float> quartile = statManager.getQuartiles();
        for (int index =0 ; index < 3; index++ ){ // quartiles are only from q1 to q3
            if (index < 2) {
                quartileString += quartile.get(index) + ", ";
            } else {
                quartileString += quartile.get(index);
            }
        }
        statQuartile.setText(quartileString);
    }
}