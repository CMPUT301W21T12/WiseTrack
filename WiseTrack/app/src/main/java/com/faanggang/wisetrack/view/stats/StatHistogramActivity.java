package com.faanggang.wisetrack.view.stats;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.StatManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.graph.Graph;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.util.ArrayList;
import java.util.List;

/**
 * Displays an experiment's activity through a histogram.
 * https://github.com/jjoe64/GraphView/wiki/Documentation
 */
public class StatHistogramActivity extends AppCompatActivity {
    private BarGraphSeries<DataPoint> series = new BarGraphSeries<>();
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
     * Queries experiment name and trial type
     * Queries trial results
     * Passes over to stats manager to create usable dataPointList
     * Creates Bar graph through graphview methods
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_histogram_screen);

        experimentManager = new ExperimentManager();
        expID = getIntent().getStringExtra("EXP_ID");

        exprName = findViewById(R.id.histogram_trial_name);
        exprTrialType = findViewById(R.id.histogram_trial_type);

        GraphView histogram = (GraphView) findViewById(R.id.stats_histogram);
        experimentQuery();
        trialDataQuery();
        histogramBounds(histogram);
        histogramStyling();
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(StatHistogramActivity.this, "Data Point clicked: (" +dataPoint.getX() + ", " + dataPoint.getY() + ")", Toast.LENGTH_SHORT).show();
            }
        });

    }


    /**
     * Modifies the bounds of the graph view
     *
     * @param histogram
     */
    public void histogramBounds(GraphView histogram){
        histogram.getViewport().setYAxisBoundsManual(true);
        histogram.getViewport().setXAxisBoundsManual(true);
        histogram.getViewport().setMinY(0);
        histogram.getViewport().setMaxY(50);

        histogram.getViewport().setMinX(0);
        histogram.getViewport().setMaxX(50);
        histogram.addSeries(series);
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
     *
     */
    public void trialDataQuery() {
        trialData.clear();
        Task<QuerySnapshot> task = db.getInstance().collection("Experiments").document(expID).collection("Trials").get();
        task.addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> documents = task.getResult().getDocuments();
                float resultValue = 0f;
                for (DocumentSnapshot doc : documents) {
                    resultValue = doc.getLong("result").floatValue();
                    trialData.add(resultValue);
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
                    series.appendData(new DataPoint(x,y),false,100);
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

    /**
     * Styling Choices for the graph
     * Top Value sizing
     * Graph Coloring
     * Bar Spacing
     */
    public void histogramStyling() {
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            public int get(DataPoint data) {
                series.setDrawValuesOnTop(true);
                series.setValuesOnTopColor(Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100));
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });
        series.setSpacing(5);
        series.setValuesOnTopSize(45);
    }
}
