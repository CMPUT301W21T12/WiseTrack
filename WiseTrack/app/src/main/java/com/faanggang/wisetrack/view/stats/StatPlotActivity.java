package com.faanggang.wisetrack.view.stats;


import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.StatManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays an experiment's data over time
 * https://github.com/jjoe64/GraphView/wiki/Documentation
 */
public class StatPlotActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series;
    private ExperimentManager experimentManager;
    private String expID;
    private StatManager statManager = new StatManager();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int trialType = -1;

    // text view one for name other for type
    private TextView exprName;
    private TextView exprTrialType;

    private List<Float> trialData = new ArrayList<Float>();
    private List<Timestamp> trialStamp = new ArrayList<Timestamp>();
    private List<DataPoint> dataPointList = new ArrayList<>();

    /**
     * Grab trial data
     * Turn trial results into usable plot data point
     * Add data points to line graph series
     * Display plot
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_plot_screen);

        experimentManager = new ExperimentManager();
        expID = getIntent().getStringExtra("EXP_ID");
        exprName = findViewById(R.id.histogram_trial_name);
        exprTrialType = findViewById(R.id.histogram_trial_type);
        GraphView plot = (GraphView) findViewById(R.id.stats_plot);


        experimentQuery();
        trialDataQuery();
        double x,y;
        x = 0;

        series = new LineGraphSeries<>();

        int points = 500;
        for (int j = 0 ; j < points ; j++) {
            x = x + 1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x,y),true,100   );
        }

        plot.addSeries(series);

    }

    /**
     * Query for a Experiment's name and trial type
     */
    public void experimentQuery() {
        experimentManager.getExperimentInfo(expID, task->{
            DocumentSnapshot docSnap = task.getResult();
            exprName.setText(docSnap.getString("name"));
            trialType = docSnap.getLong("trialType").intValue();
            if (trialType == 0) {
                exprTrialType.setText("[Count]");;
            } else if (trialType == 1) {
                exprTrialType.setText("[Binomial Trial]");
            } else if (trialType == 2) {
                exprTrialType.setText(" [Non-negative Integer Count]");
            } else if (trialType == 3) {
                exprTrialType.setText("[Measurement Trial]");
            } else {
                exprTrialType.setText("[Unknown Unicorn]");// invalid
            }
        });

    }
    /**
     * Query for Trial's data
     */
    public void trialDataQuery() {
        trialData.clear();
        Task<QuerySnapshot> task = db.getInstance().collection("Experiments").document(expID).collection("Trials").orderBy("date").get();
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                float resultValue = 0f;
                Timestamp dateStamp = null;
                for (DocumentSnapshot doc : documents) {
                    resultValue = doc.getLong("result").floatValue();
                    dateStamp = doc.getTimestamp("date");

                    trialData.add(resultValue);
                    trialStamp.add(dateStamp);
                    Log.i("results log ONE", String.valueOf(resultValue) + "::" + String.valueOf(trialType));
                }
                double x,y;
                x = 0;
                y = 0;
                Log.i("results log TWO", trialData.toString() + "::" + String.valueOf(trialType));
                dataPointList = statManager.generateStatHistogram(trialData,trialType);
                Log.i("results log THREE", trialData.toString() + "::" + String.valueOf(trialType));
                for (int j = 0 ; j < dataPointList.size(); j ++ ) {
                    y= dataPointList.get(j).getY();
                    x= dataPointList.get(j).getX();;
                    series.appendData(new DataPoint(x,y),true,100);
                }
            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Stats Histogram Activity: ", "Trial Data NOT FOUND.");
            }
        });
    }




}
