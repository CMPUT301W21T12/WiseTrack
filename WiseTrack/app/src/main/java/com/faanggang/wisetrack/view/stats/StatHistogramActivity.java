package com.faanggang.wisetrack.view.stats;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.StatManager;
import com.google.common.graph.Graph;
import com.google.firebase.firestore.DocumentSnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

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
    private Long trialType;

    // text view one for name other for type
    private TextView exprName;
    private TextView exprTrialType;

    private List<Float> trialData = new ArrayList<Float>();

    /**
     * Grab experiment and trial data
     * draw colors
     * display graph
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_histogram_screen);

        GraphView histogram = (GraphView) findViewById(R.id.stats_histogram);

        double x,y;
        x = 0;

        int bars = 100 ;
        for (int j = 0 ; j < bars; j++ ) {
            if (j%2 ==0 ) {
                y = Math.sin(j);
            } else {
                y = -Math.sin(j);
            }

            x =j;
            series.appendData(new DataPoint(x,y),true,100   );
        }
        histogram.addSeries(series);

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            public int get(DataPoint data) {
                series.setDrawValuesOnTop(true);
                series.setValuesOnTopColor(Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100));
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

    // draw values on top
       series.setValuesOnTopSize(50);

    }
    /**
     * Query for a Experiment's name
     */
    public void experimentQuery() {
        experimentManager.getExperimentInfo(expID, task->{
            DocumentSnapshot docSnap = task.getResult();
            exprName.setText(docSnap.getString("name"));
            //trialType = docSnap.getLong("trialType");
        });

    }
}
