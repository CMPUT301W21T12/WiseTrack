package com.faanggang.wisetrack.view.publish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.view.MainActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.PublishingManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * PublishExperiment4_Complete Activity:
 * Display the summary about the experiment, based on what the user has entered.
 * User must click "Publish" to confirm and add experiment to FireBase.
 * Return back to MainActivity.
 */
public class PublishExperiment4_Complete extends AppCompatActivity
    implements View.OnClickListener{

    private TextView experiment_description;
    private Button publish;
    private Button cancel;
    private ArrayList<String> keywords;
    private String name, description, region;
    private int minTrials, trialType;
    private boolean geolocation;
    private static final String TAG = "DocSnippets";
    private FirebaseAuth mAuth;
    private PublishingManager publishingManager;
    private Experiment currentExperiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_experiment_complete);

        mAuth = FirebaseAuth.getInstance();
        publishingManager = new PublishingManager(FirebaseFirestore.getInstance());
        experiment_description = findViewById(R.id.publish4_description);
        publish = findViewById(R.id.publish4_publish_button);
        cancel = findViewById(R.id.publish4_cancel_button);
        keywords = new ArrayList<>();
        Bundle extras = getIntent().getExtras();

        name = extras.getString("EXTRA_NAME");
        description = extras.getString("EXTRA_DESCRIPTION");
        region = extras.getString("EXTRA_REGION");
        minTrials = extras.getInt("EXTRA_MIN_TRIALS");
        trialType = extras.getInt("EXTRA_TRIAL_TYPE");
        geolocation = extras.getBoolean("EXTRA_GEOLOCATION");
        currentExperiment = new Experiment(name, description, region, minTrials, trialType,
                geolocation, new Date(), mAuth.getUid());

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
                "Experiment name: " + name + "\n\n" +
                "Description: " + description + "\n\n" +
                "Region: " + region + "\n" +
                "Minimum trials: " + minTrials_str + "\n" +
                "Trial type: " + trialType_str + "\n" +
                "Geolocation required: " + geolocation_str;

        experiment_description.setText(text_description);

        publish.setOnClickListener(this);
        cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.publish4_publish_button) {

            // Firebase:
            // Add experiment document data to "Experiments" collection with auto-generated id
            Map<String, Object> experimentHashMap = publishingManager
                    .createExperimentMap(currentExperiment, WiseTrackApplication.getCurrentUser()
                            .getUserName());
            try {
                publishingManager.publishExperiment(experimentHashMap);
            } catch (Exception e) {
                Log.e(TAG, "Error trying to publish experiment: " + e.getMessage());
            }

        }

        // GO back to main
        Intent intent = new Intent(PublishExperiment4_Complete.this, MainActivity.class);
        startActivity(intent);

    }
}