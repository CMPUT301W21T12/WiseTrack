package com.faanggang.wisetrack.view.stats;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.faanggang.wisetrack.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Displays an experiment's data over time
 */
public class StatPlotActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stats_plot_screen);

        double x,y;
        x = 0;

        GraphView graph = (GraphView) findViewById(R.id.stats_plot);
        series = new LineGraphSeries<>();


        int num = 500;
        for (int j = 0 ; j < num ; j++) {
            x = x +0.1;
            y = Math.sin(x);
            series.appendData(new DataPoint(x,y),true,100   );
        }

        graph.addSeries(series);

    }


}
