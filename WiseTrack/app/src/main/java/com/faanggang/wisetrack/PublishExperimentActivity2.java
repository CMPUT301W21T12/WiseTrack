package com.faanggang.wisetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.EmptyStackException;

public class PublishExperimentActivity2 extends AppCompatActivity
    implements View.OnClickListener{

    private Button counts;
    private Button binomial;
    private Button nonNegative;
    private Button measurements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_experiment2);

        counts = findViewById(R.id.counts_button);
        binomial = findViewById(R.id.binomial_button);
        nonNegative = findViewById(R.id.non_negative_button);
        measurements = findViewById(R.id.measurements_button);

        counts.setOnClickListener(this);
        binomial.setOnClickListener(this);
        nonNegative.setOnClickListener(this);
        measurements.setOnClickListener(this);
        
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(PublishExperimentActivity2.this, PublishExperimentActivity3.class);

        Bundle extras = getIntent().getExtras();

        // put in previous extras/variables to the next intent so that the next activity
        // can access them
        if( extras != null)
            intent.putExtras(extras);
        
        int trialType;

        if (v.getId() == R.id.counts_button)
                trialType = 0;
        else if (v.getId() == R.id.binomial_button)
                trialType = 1;
        else if (v.getId() == R.id.non_negative_button)
                trialType = 2;
        else if (v.getId() == R.id.measurements_button)
                trialType = 3;
        else
            throw new EmptyStackException();

        intent.putExtra("EXTRA_TRIAL_TYPE", trialType);

        startActivity(intent);

    }
}