package com.faanggang.wisetrack.view.trial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExecuteTrialController;
import com.faanggang.wisetrack.model.executeTrial.MeasurementTrial;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.Map;

public class ExecuteMeasurementActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Snippets";
    private EditText trialData;
    private EditText trialGeolocation;

    private FirebaseAuth mAuth;
    private ExecuteTrialController executeTrialController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_measurement);

        Bundle extras = getIntent().getExtras();

        executeTrialController = new ExecuteTrialController(extras.getString("EXP_ID"));
        mAuth = FirebaseAuth.getInstance();

        trialData = findViewById(R.id.trial_data_input);
        // hardcoded address for now; will implement android map fragment later
        trialGeolocation = findViewById(R.id.trial_geolocation_input);

        Button cancelButton = findViewById(R.id.button_cancel);
        Button saveButton = findViewById(R.id.button_save);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        float data = 0;

        // null value handling
        if (v.getId() == R.id.button_save) {
            if (trialData.getText() != null) {
                // measurement field not empty
                data = Float.parseFloat(trialData.getText().toString());
            }
            String geolocation = trialGeolocation.getText().toString();

            MeasurementTrial currentTrial = new MeasurementTrial(data, geolocation, mAuth.getUid(), new Date(), 3);

            // create and store current trial into firebase
            Map<String, Object> TrialHashMap = executeTrialController.createTrialDocument(currentTrial);
            try {
                executeTrialController.executeTrial(TrialHashMap);
            } catch (Exception e) {
                Log.e(TAG, "Error trying to execute measurement trial: " + e.getMessage());
            }

            Toast.makeText(this, "Trial result saved", Toast.LENGTH_SHORT).show();
            finish();  // return to previous activity
        } else if (v.getId() == R.id.button_cancel) {
            finish();
        }
    }
}