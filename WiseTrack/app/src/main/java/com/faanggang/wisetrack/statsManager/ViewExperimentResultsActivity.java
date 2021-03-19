package com.faanggang.wisetrack.statsManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.executeTrial.ExecuteCountActivity;
import com.faanggang.wisetrack.experiment.ViewExperimentActivity;


/**
 * Screen to select which type of statistic a user would like to view
 *
 */
public class ViewExperimentResultsActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_experiment_results);
        Bundle extras = getIntent().getExtras();

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
            Toast.makeText(this, "STATS REPORT", Toast.LENGTH_SHORT).show();
            //Intent reportIntent = new Intent(ViewExperimentResultsActivity.this, StatReportActivity.class);
            //startActivity(reportIntent);

        } else if (v.getId() == R.id.view_histogram_button) {
            Toast.makeText(this, "HISTOGRAM (UNAVAILABLE)", Toast.LENGTH_SHORT).show();
        } else if (v.getId() == R.id.view_plots_button) {
            Toast.makeText(this, "PLOT (UNAVAILABLE)", Toast.LENGTH_SHORT).show();
        }
    }
}

