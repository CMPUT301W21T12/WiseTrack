package com.faanggang.wisetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PublishExperimentActivity2 extends AppCompatActivity {

    private Button counts;
    private Button binomial;
    private Button nonNegative;
    private Button measurements;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_experiment2);

        Intent intent = getIntent();

        counts = findViewById(R.id.counts_button);
        binomial = findViewById(R.id.counts_button);
        nonNegative = findViewById(R.id.non_negative_button);
        measurements = findViewById(R.id.measurements_button);


        counts.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent nextIntent = new Intent(PublishExperimentActivity2.this, PublishExperimentActivity3.class);
                intent.putExtra("EXTRA_TRIAL_TYPE", 0);
                startActivity(nextIntent);
            }
        });

        binomial.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent nextIntent = new Intent(PublishExperimentActivity2.this, PublishExperimentActivity3.class);
                intent.putExtra("EXTRA_TRIAL_TYPE", 1);
                startActivity(nextIntent);
            }
        });

        nonNegative.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent nextIntent = new Intent(PublishExperimentActivity2.this, PublishExperimentActivity3.class);
                intent.putExtra("EXTRA_TRIAL_TYPE", 2);
                startActivity(nextIntent);

            }
        });

        measurements.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent nextIntent = new Intent(PublishExperimentActivity2.this, PublishExperimentActivity3.class);
                intent.putExtra("EXTRA_TRIAL_TYPE", 3);
                startActivity(nextIntent);

            }
        });

    }
}