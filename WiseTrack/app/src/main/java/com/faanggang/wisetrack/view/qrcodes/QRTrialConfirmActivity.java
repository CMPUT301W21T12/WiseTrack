package com.faanggang.wisetrack.view.qrcodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExecuteTrialController;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.GeolocationManager;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QRTrialConfirmActivity extends AppCompatActivity {
    private ExperimentManager experimentManager;
    private ExecuteTrialController trialController;
    private TextView expNameView;
    private TextView trialResultView;
    private TextView geoLocationView;
    private Button confirm_button;
    private Button cancel_button;
    private String expID;
    private long trialResult;
    private Boolean geolocationNecessary;
    private GeolocationManager geoManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        expID = intent.getStringExtra("EXP_ID");
        trialResult = intent.getLongExtra("TRIAL_RESULT",-1);

        setContentView(R.layout.activity_qr_trial_confirm);
        expNameView = findViewById(R.id.qr_experiment_name);
        geoLocationView = findViewById(R.id.qr_geolocation);
        trialResultView = findViewById(R.id.qr_trial_result);
        confirm_button = findViewById(R.id.qr_trial_confirm);
        cancel_button = findViewById(R.id.qr_trial_cancel);
        experimentManager = new ExperimentManager();
        trialController = new ExecuteTrialController(expID);
        geoManager = GeolocationManager.getInstance(this);
        setTextDetails(expID, trialResult);


        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmTrial();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelTrial();
            }
        });
    }







    private void confirmTrial(){
        Map<String, Object> data = new HashMap<>();
        Location location = geoManager.getLastLocation();
        if (geolocationNecessary && location != null){
            data.put("geolocation", geoManager.getLastLocation());
        } else if (geolocationNecessary && location == null) {
            Toast.makeText(getApplicationContext(), "Location Services Not Enabled", Toast.LENGTH_SHORT).show();
            finish();
        } else{
            data.put("geolocation", null);
        }
        data.put("result", trialResult);
        data.put("date", new Date());
        data.put("conductor id", WiseTrackApplication.getCurrentUser().getUserID());
        trialController.executeTrial(data);
        Toast.makeText(getApplicationContext(), "Trial Successfully Uploaded", Toast.LENGTH_SHORT).show();
        finish();
    }
    private void cancelTrial(){
        finish();
    }


    private void setTextDetails(String expID, long result) {
        experimentManager.getExperimentInfo(expID, task ->{
            DocumentSnapshot doc = task.getResult();
            if (doc.exists()){
                expNameView.setText(doc.getString("name"));
                int trialType = doc.getLong("trialType").intValue();
                switch (trialType){
                case 0:
                    trialResultView.setText("Result: Add 1 Count");
                    break;
                case 1:
                    if (result!=0){
                        trialResultView.setText("Result: Success");
                    } else{
                        trialResultView.setText("Result: Fail");
                    }
                    break;
                case 2:
                    trialResultView.setText("Result: " + String.valueOf(result));
                }
                geolocationNecessary = doc.getBoolean("geolocation");
                geoLocationView.setText("GeoLocation Required: " + geolocationNecessary.toString());
            }
        });
    }


}