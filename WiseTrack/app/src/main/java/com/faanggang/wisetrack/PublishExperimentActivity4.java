package com.faanggang.wisetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PublishExperimentActivity4 extends AppCompatActivity
    implements View.OnClickListener{

    private TextView experiment_description;
    private Button publish;
    private Button cancel;
    private String name, description, region;
    private int minTrials, trialType;
    private boolean geolocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_experiment4);

        experiment_description = findViewById(R.id.publish4_description);
        publish = findViewById(R.id.publish4_publish_button);
        cancel = findViewById(R.id.publish4_cancel_button);

        Bundle extras = getIntent().getExtras();

        name = extras.getString("EXTRA_NAME");
        description = extras.getString("EXTRA_DESCRIPTION");
        region = extras.getString("EXTRA_REGION");
        minTrials = extras.getInt("EXTRA_MIN_TRIALS");
        trialType = extras.getInt("EXTRA_TRIAL_TYPE");
        geolocation = extras.getBoolean("EXTRA_GEOLOCATION");

        String minTrials_str = String.valueOf(minTrials);
        String trialType_str;
        String geolocation_str = String.valueOf(geolocation);

        switch(trialType) {
            case 0:
                trialType_str = "Count";
                break;
            case 1:
                trialType_str = "Binomial trials";
                break;
            case 2:
                trialType_str = "Non-negative integer counts";
                break;
            case 3:
                trialType_str = "Measurement trials";
                break;
            default:
                trialType_str = "";
        }

        String text_description =
                "Experiment name: " + name + "\n" +
                "Description: " + description + "\n" +
                "Region: " + region + "\n" +
                "Minimum trials: " + minTrials_str + "\n" +
                "Trial type: " + trialType_str + "\n" +
                "Geolocation required: " + geolocation_str;

        experiment_description.setText(text_description);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.publish4_publish_button) {
            float [] geolocation_arr = null;

            if(geolocation)
                geolocation_arr = new float[2];

            Experiment experiment = new Experiment(
                    name, description, region, minTrials, trialType, geolocation_arr);
        }

    }
}