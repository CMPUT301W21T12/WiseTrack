package com.faanggang.wisetrack.executeTrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.experiment.ViewExperimentActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ExecuteCountActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Snippets";
    private EditText trialData;
    private EditText trialGeolocation;
    private EditText trialDescription;

    private FirebaseAuth mAuth;
    private ExecuteTrialController executeTrialController;

    private final Bundle extras = getIntent().getExtras();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_count);

        executeTrialController = new ExecuteTrialController(extras.getString("EXP_ID"));
        mAuth = FirebaseAuth.getInstance();

        trialData = findViewById(R.id.trial_data_input);
        // hardcoded address for now; will implement android map fragment later
        trialGeolocation = findViewById(R.id.trial_geolocation_input);
        trialDescription = findViewById(R.id.trial_description_input);

        Button cancelButton = findViewById(R.id.button_cancel);
        Button saveButton = findViewById(R.id.button_save);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int count = 0;

        if (v.getId() == R.id.button_save) {
            if (trialData.getText() != null) {
                // count data field is not empty
                count = Integer.parseInt(trialData.getText().toString());
            }

            String geolocation = trialGeolocation.getText().toString();
            String description = trialDescription.getText().toString();

            int trialType = extras.getInt("trialType");
            CountTrial currentTrial = new CountTrial(count, trialType, geolocation, description, mAuth.getUid(), new Date());

            // create and store current trial into firebase
            Map<String, Object> TrialHashMap = executeTrialController.CreateTrialDocument(currentTrial);
            try {
                executeTrialController.executeTrial(TrialHashMap);
            } catch (Exception e) {
                Log.e(TAG, "Error trying to execute binomial trial: " + e.getMessage());
            }

            Toast.makeText(this, "Trial result experiment", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.button_cancel) {
            // return to experiment detail screen
            intent = new Intent(ExecuteCountActivity.this, ViewExperimentActivity.class);
            startActivity(intent);
        }
        intent = new Intent(ExecuteCountActivity.this, ViewExperimentActivity.class);
        startActivity(intent);

    }
}