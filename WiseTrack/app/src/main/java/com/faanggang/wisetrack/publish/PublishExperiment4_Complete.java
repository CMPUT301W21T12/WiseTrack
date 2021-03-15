package com.faanggang.wisetrack.publish;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.Experiment;
import com.faanggang.wisetrack.MainActivity;
import com.faanggang.wisetrack.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PublishExperiment4_Complete extends AppCompatActivity
    implements View.OnClickListener{

    private TextView experiment_description;
    private Button publish;
    private Button cancel;
    private ArrayList<String> keywords;
    private String name, description, region;
    private int minTrials, crowdSource;
    private boolean geolocation;
    private static final String TAG = "DocSnippets";
    private FirebaseAuth mAuth;
    private PublishingController publishingController;
    private Experiment currentExperiment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_experiment_complete);

        mAuth = FirebaseAuth.getInstance();
        publishingController = new PublishingController();
        experiment_description = findViewById(R.id.publish4_description);
        publish = findViewById(R.id.publish4_publish_button);
        cancel = findViewById(R.id.publish4_cancel_button);
        keywords = new ArrayList<>();
        Bundle extras = getIntent().getExtras();

        name = extras.getString("EXTRA_NAME");
        description = extras.getString("EXTRA_DESCRIPTION");
        region = extras.getString("EXTRA_REGION");
        minTrials = extras.getInt("EXTRA_MIN_TRIALS");
        crowdSource = extras.getInt("EXTRA_TRIAL_TYPE");
        geolocation = extras.getBoolean("EXTRA_GEOLOCATION");
        currentExperiment = new Experiment(name, description, region, minTrials, crowdSource,
                geolocation, new Date(), mAuth.getUid());

        String minTrials_str = String.valueOf(minTrials);
        String crowdSource_str;
        String geolocation_str = String.valueOf(geolocation);

        switch(crowdSource) {
            case 0:
                crowdSource_str = "Count";
                break;
            case 1:
                crowdSource_str = "Binomial trials";
                break;
            case 2:
                crowdSource_str = "Non-negative integer counts";
                break;
            case 3:
                crowdSource_str = "Measurement trials";
                break;
            default:
                crowdSource_str = "";
        }

        String text_description =
                "Experiment name: " + name + "\n\n" +
                "Description: " + description + "\n\n" +
                "Region: " + region + "\n" +
                "Minimum trials: " + minTrials_str + "\n" +
                "Trial type: " + crowdSource_str + "\n" +
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
            Map<String, Object> experimentHashMap = publishingController
                    .createExperimentHashMap(currentExperiment);
            try {
                publishingController.publishExperiment(experimentHashMap);
            } catch (Exception e) {
                Log.e(TAG, "Error trying to publish experiment: " + e.getMessage());
            }

        // GO back to main
        Intent intent = new Intent(PublishExperiment4_Complete.this, MainActivity.class);
        startActivity(intent);

        }
    }
}