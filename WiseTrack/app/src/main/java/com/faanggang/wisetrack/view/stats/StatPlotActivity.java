package com.faanggang.wisetrack.view.stats;


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
import com.faanggang.wisetrack.model.stats.StatPlot;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Displays an experiment's data over time
 * https://github.com/jjoe64/GraphView/wiki/Documentation
 */
public class StatPlotActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
    private ExperimentManager experimentManager;
    private String expID;
    private StatManager statManager = new StatManager();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private int trialType = -1;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd");
    // text view one for name other for type
    private TextView exprName;
    private TextView exprTrialType;

    private List<Float> trialData = new ArrayList<Float>();
    private List<Timestamp> trialStamp = new ArrayList<Timestamp>();
    private List<DataPoint> dataPointList = new ArrayList<>();
    private int maxValue = 0;

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
        exprName = findViewById(R.id.plot_trial_name);
        exprTrialType = findViewById(R.id.plot_trial_type);
        GraphView plot = (GraphView) findViewById(R.id.stats_plot);

        experimentQuery();
        trialDataQuery();
        lineBounds(plot);
        lineStyling();
        lineAxisFormatter(plot);
        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(StatPlotActivity.this, "Data Point clicked: (" + getDateTimeString(dataPoint.getX()) + ", " + dataPoint.getY() + ")", Toast.LENGTH_SHORT).show();
            }
        });
    }



    /**
     * This function converts the double type to date then formatted into a string
     * @return Date and time that the comment was made.
     */
    public String getDateTimeString(Double dValue) {
        long longValue = ((long) (dValue * 1));
        Date date = new Date(longValue);
        return new SimpleDateFormat("MM-dd hh:mm a").format(date);
    }

    /**
     *
     */
    public void lineAxisFormatter(GraphView plot) {
        plot.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter()
        {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    return sdf.format(new Date((long) value));
                } else {
                    return super.formatLabel(value, isValueX);
                }

            }
        });
        plot.getGridLabelRenderer().setNumHorizontalLabels(5);

    }
    /**
     * Line styling
     * sets color and point thickness
     */
    public void lineStyling() {
        series.setColor(Color.BLUE);
        series.setDrawDataPoints(true);
        series.setDataPointsRadius(16);
        series.setThickness(8);
    }

    /**
     * line bounds
     * add data points to the plot
     * indicate bounds
     */
    public void lineBounds(GraphView plot) {
        Log.i("GRAPHVIEW", dataPointList.toString());
        int maxX = 31;
        plot.getViewport().setYAxisBoundsManual(true);
        plot.getViewport().setMinY(0);
        plot.getViewport().setMaxY(20);
        plot.getViewport().setMinX(0);
        plot.getViewport().setMaxX(maxX);
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
        Task<QuerySnapshot> task = db.getInstance()
                .collection("Experiments")
                .document(expID)
                .collection("Trials")
                .orderBy("date", Query.Direction.ASCENDING)
                .get();
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

                }
                double x,y;
                x = 0;
                y = 0;
                Log.i("StatPlotActivity Log 1", trialData.toString() + "::" + trialStamp.toString()  +trialType);
                dataPointList = statManager.generateStatPlot(trialData, trialStamp, trialType);
                for (int j = 0 ; j < dataPointList.size(); j ++ ) {
                    y= dataPointList.get(j).getY();
                    x= dataPointList.get(j).getX();
                    series.appendData(new DataPoint(x,y),false,100);
                }
                maxValue =  (int)dataPointList.get(dataPointList.size()-1).getY();
                Log.i("STATS PLOT ACTIVITY: ", dataPointList.toString());

            }
        });
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w("Stats Plot Activity: ", "Trial Data NOT FOUND.");
            }
        });
    }




}
