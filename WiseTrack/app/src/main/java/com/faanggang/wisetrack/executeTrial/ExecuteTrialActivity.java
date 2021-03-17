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

public class ExecuteTrialActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText trialData;
    private EditText trialGeolocation;
    private EditText trialDescription;
    private Button cancelButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_trial);

        trialData = findViewById(R.id.trial_data_input);
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


        if (v.getId() == R.id.button_save) {
            String data = trialData.getText().toString();
            String geolocation = trialGeolocation.getText().toString();
            String description = trialDescription.getText().toString();

            Toast.makeText(this, "Trial data saved to experiment", Toast.LENGTH_SHORT).show();

        } else if (v.getId() == R.id.button_cancel) {
            // return to experiment detail screen
            intent = new Intent(ExecuteTrialActivity.this, ViewExperimentActivity.class);
        }
        //startActivity(intent);

    }
}