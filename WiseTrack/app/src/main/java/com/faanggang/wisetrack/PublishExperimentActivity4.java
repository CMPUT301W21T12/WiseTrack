package com.faanggang.wisetrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PublishExperimentActivity4 extends AppCompatActivity
    implements View.OnClickListener{

    private TextView experiment_description;
    private Button publish;
    private Button cancel;
    private String name, description, region;
    private int minTrials, crowdSource;
    private boolean geolocation;
    private static final String TAG = "DocSnippets";

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
        crowdSource = extras.getInt("EXTRA_TRIAL_TYPE");
        geolocation = extras.getBoolean("EXTRA_GEOLOCATION");

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

        if(v.getId() == R.id.publish4_publish_button) {

            // Firebase:
            // Add experiment document data to "Experiments" collection with auto-generated id
            Map<String, Object> data = new HashMap<>();
            data.put("name", name);
            data.put("description", description);
            data.put("region", region);
            data.put("minTrials", minTrials);
            data.put("crowdSource", crowdSource);
            data.put("geolocation", geolocation);

            FirebaseFirestore db = FirebaseFirestore.getInstance();;

            db.collection("Experiments")
                    .add(data)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }

        // GO back to main
        Intent intent = new Intent(PublishExperimentActivity4.this, MainActivity.class);
        startActivity(intent);

    }
}