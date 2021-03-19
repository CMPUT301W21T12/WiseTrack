package com.faanggang.wisetrack.executeTrial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class ExecuteBinomialActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText successCount;
    private EditText failureCount;
    private EditText trialGeolocation;
    private EditText trialDescription;
    private Button cancelButton;
    private Button saveButton;

    private FirebaseAuth mAuth;
    private ExecuteTrialController executeTrialController;

    private BinomialTrial currentTrial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_binomial);

        executeTrialController = new ExecuteTrialController();
        mAuth = FirebaseAuth.getInstance();

        successCount = findViewById(R.id.success_count_input);
        failureCount = findViewById(R.id.failure_count_input);
        // hardcoded address for now; will implement android map fragment later
        trialGeolocation = findViewById(R.id.trial_geolocation_input);
        trialDescription = findViewById(R.id.trial_description_input);

        cancelButton = findViewById(R.id.button_cancel);
        saveButton = findViewById(R.id.button_save);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int success, failure;

        // null value handling
        if (v.getId() == R.id.button_save) {
            if (successCount.getText() != null) {
                // successCount field filled
                success = Integer.parseInt(successCount.getText().toString());
            } else {
                success = 0;  // default set to 0 number of success count
            }
            if (failureCount.getText() != null) {
                // failureCount field filled
                failure = Integer.parseInt(failureCount.getText().toString());
            } else {
                failure = 0;  // default set to 0 number of failure count
            }

            String geolocation = trialGeolocation.getText().toString();
            String description = trialDescription.getText().toString();

            currentTrial = new BinomialTrial(success, failure, geolocation, description, mAuth.getUid(), new Date());

            Map<String, Object> TrialHashMap = new HashMap<>();
            Toast.makeText(this, "Trial result experiment", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.button_cancel) {
            // return to experiment detail screen
            intent = new Intent(ExecuteBinomialActivity.this, ViewExperimentActivity.class);
        }
        intent = new Intent(ExecuteBinomialActivity.this, ViewExperimentActivity.class);
        startActivity(intent);
    }
}