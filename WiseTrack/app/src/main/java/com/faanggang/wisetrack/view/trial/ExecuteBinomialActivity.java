package com.faanggang.wisetrack.view.trial;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.executeTrial.BinomialTrial;
import com.faanggang.wisetrack.controllers.ExecuteTrialController;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.Map;

public class ExecuteBinomialActivity extends AppCompatActivity implements View.OnClickListener {
    private int trialResult;
    private static final String TAG = "Snippets";
    private Spinner dropdown;
    ArrayAdapter<String> adapter;
    private Location geolocation;
    private FirebaseAuth mAuth;
    private ExecuteTrialController executeTrialController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_binomial);

        Bundle extras = getIntent().getExtras();
        executeTrialController = new ExecuteTrialController(extras.getString("EXP_ID"));
        mAuth = FirebaseAuth.getInstance();
        if (extras.get("GEOLOCATION") != null) {
            geolocation = (Location) extras.get("GEOLOCATION");
        } else {
            geolocation = null;
        }
        // get the spinner
        dropdown = findViewById(R.id.spinner_select_trial_result);
        // create string adapter for the spinner and populate it
        adapter = new ArrayAdapter<String>(ExecuteBinomialActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.binomial_result_items));
        // set dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);  // attach data adapter to spinner

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                    // nothing selected
                    Toast.makeText(parent.getContext(), "Warning: No trial result selected!\n" +
                            "Default: Invalid Input", Toast.LENGTH_SHORT).show();
                    trialResult = -1;  // set default value
                } else {
                    String item = parent.getItemAtPosition(position).toString();

                    if (item.equals("Success")) {
                        Toast.makeText(parent.getContext(), "Selected: Success", Toast.LENGTH_SHORT).show();
                        trialResult = 1;
                    } else if (item.equals("Failure")) {
                        Toast.makeText(parent.getContext(), "Selected: Failure", Toast.LENGTH_SHORT).show();
                        trialResult = 0;
                    } else {
                        try {
                            throw new Exception("No valid spinner item selection detected.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Warning: No trial result selected!", Toast.LENGTH_SHORT).show();
                // invokes automatic callback interface
            }
        });

        // hardcoded address for now; will implement android map fragment later

        Button cancelButton = findViewById(R.id.button_cancel);
        Button saveButton = findViewById(R.id.button_save);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button_save) {

            BinomialTrial currentTrial = new BinomialTrial(trialResult, geolocation, mAuth.getUid(), new Date());

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