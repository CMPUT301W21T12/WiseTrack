package com.faanggang.wisetrack.view.stats;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.stats.StatHistogram;
import com.faanggang.wisetrack.model.stats.StatPlot;


/**
 * Screen to select which type of statistic a user would like to view
 *
 */
public class ViewExperimentResultsActivity extends AppCompatActivity implements View.OnClickListener {
    private String expID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_experiment_results);

        expID = getIntent().getStringExtra("EXP_ID");

        Button reportButton = findViewById(R.id.view_statistics_button);
        Button histogramButton = findViewById(R.id.view_histogram_button);
        Button plotButton = findViewById(R.id.view_plots_button);

        reportButton.setOnClickListener(this);
        histogramButton.setOnClickListener(this);
        plotButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.view_statistics_button) {
            Toast.makeText(this, "STATISTICS REPORT", Toast.LENGTH_SHORT).show();
            Intent reportIntent = new Intent(ViewExperimentResultsActivity.this, StatReportActivity.class);
            reportIntent.putExtra("EXP_ID", expID);
            startActivity(reportIntent);

        } else if (v.getId() == R.id.view_histogram_button) {
            Toast.makeText(this, "HISTOGRAM ", Toast.LENGTH_SHORT).show();
            Intent histogramIntent = new Intent(ViewExperimentResultsActivity.this, StatHistogramActivity.class);
            histogramIntent.putExtra("EXP_ID", expID);
            startActivity(histogramIntent);
        } else if (v.getId() == R.id.view_plots_button) {
            Toast.makeText(this, "PLOT OVER TIME", Toast.LENGTH_SHORT).show();
            Intent plotIntent = new Intent(ViewExperimentResultsActivity.this, StatPlotActivity.class);
            plotIntent.putExtra("EXP_ID", expID);
            startActivity(plotIntent);
        }
    }
}

