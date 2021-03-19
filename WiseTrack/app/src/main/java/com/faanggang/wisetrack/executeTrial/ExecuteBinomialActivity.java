package com.faanggang.wisetrack.executeTrial;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.Map;

public class ExecuteBinomialActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Snippets";
    private EditText successCount;
    private EditText failureCount;
    private EditText trialGeolocation;
    private EditText trialDescription;

    private FirebaseAuth mAuth;
    private ExecuteTrialController executeTrialController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_binomial);

        Bundle extras = getIntent().getExtras();

        executeTrialController = new ExecuteTrialController(extras.getString("EXP_ID"));
        mAuth = FirebaseAuth.getInstance();

        successCount = findViewById(R.id.success_count_input);
        failureCount = findViewById(R.id.failure_count_input);
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
        int success = 0, failure = 0;  // default set to 0 number of success count

        // null value handling
        if (v.getId() == R.id.button_save) {
            if (successCount.getText() != null) {
                // successCount field filled
                success = Integer.parseInt(successCount.getText().toString());
            }
            if (failureCount.getText() != null) {
                // failureCount field filled
                failure = Integer.parseInt(failureCount.getText().toString());
            }

            String geolocation = trialGeolocation.getText().toString();
            String description = trialDescription.getText().toString();

            BinomialTrial currentTrial = new BinomialTrial(success, failure, geolocation, description, mAuth.getUid(), new Date());

            // create and store current trial into firebase
            Map<String, Object> TrialHashMap = executeTrialController.createTrialDocument(currentTrial);
            try {
                executeTrialController.executeTrial(TrialHashMap);
            } catch (Exception e) {
                Log.e(TAG, "Error trying to execute binomial trial: " + e.getMessage());
            }

            Toast.makeText(this, "Trial result saved", Toast.LENGTH_SHORT).show();
            finish();  // return to previous activity
        } else if (v.getId() == R.id.button_cancel) {
            // return to experiment detail screen
            finish();
        }

    }
}