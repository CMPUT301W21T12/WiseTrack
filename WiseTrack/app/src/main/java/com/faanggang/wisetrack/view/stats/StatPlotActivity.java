package com.faanggang.wisetrack.view.stats;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.StatManager;
import com.google.firebase.firestore.DocumentSnapshot;
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
    private Long trialType;

    // text view one for name other for type
    private TextView exprName;
    private TextView exprTrialType;

    private List<Float> trialData = new ArrayList<Float>();

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

        double x,y;
        x = 0;

        GraphView graph = (GraphView) findViewById(R.id.stats_plot);
        series = new LineGraphSeries<>();
        // Method call to stats manager

        int num = 500;
        for (int j = 0 ; j < num ; j++) {
            x = x +0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x,y),true,100   );
        }

        graph.addSeries(series);

    }

    /**
     * Query for a Experiment's name
     */
    public void experimentQuery() {
        experimentManager.getExperimentInfo(expID, task -> {
            DocumentSnapshot docSnap = task.getResult();
            exprName.setText(docSnap.getString("name"));
            //trialType = docSnap.getLong("trialType");
        });
    }



}
