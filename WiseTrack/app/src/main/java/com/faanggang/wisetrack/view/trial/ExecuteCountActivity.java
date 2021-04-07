package com.faanggang.wisetrack.view.trial;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.executeTrial.CountTrial;
import com.faanggang.wisetrack.controllers.ExecuteTrialController;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.Map;

public class ExecuteCountActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "Snippets";
    private EditText trialData;
    private TextView oneCount;
    private Location geolocation;

    int trialType;

    private FirebaseAuth mAuth;
    private ExecuteTrialController executeTrialController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_count);

        Bundle extras = getIntent().getExtras();
        executeTrialController = new ExecuteTrialController(extras.getString("EXP_ID"));
        mAuth = FirebaseAuth.getInstance();
        if (extras.get("GEOLOCATION") != null) {
            geolocation = (Location) extras.get("GEOLOCATION");
        } else {
            geolocation = null;
        }

        oneCount = findViewById(R.id.textview_one_count);
        trialData = findViewById(R.id.trial_data_input);
        // hardcoded address for now; will implement android map fragment later

        Button cancelButton = findViewById(R.id.button_cancel);
        Button saveButton = findViewById(R.id.button_save);

        trialType = extras.getInt("trialType");
        if (trialType == 0) {  // count type
            trialData.setVisibility(View.GONE);
        } else if (trialType == 2) {  // nonngeative int type
            oneCount.setVisibility(View.INVISIBLE);
        }

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_save) {
            int trialResult = 1;  // default set to one count
            if (trialType == 2) {
                trialResult = Integer.parseInt(trialData.getText().toString());
            }


            CountTrial currentTrial = new CountTrial(trialResult, geolocation, mAuth.getUid(), new Date(), trialType);

            // create and store current trial into firebase
            Map<String, Object> TrialHashMap = executeTrialController.createTrialDocument(currentTrial);
            try {
                executeTrialController.executeTrial(TrialHashMap);
            } catch (Exception e) {
                Log.e(TAG, "Error trying to execute count trial: " + e.getMessage());
            }

            Toast.makeText(this, "Trial result saved", Toast.LENGTH_SHORT).show();
            finish();  // return to previous activity
        } else if (v.getId() == R.id.button_cancel) {
            // return to experiment detail screen
            finish();
        }
    }
}