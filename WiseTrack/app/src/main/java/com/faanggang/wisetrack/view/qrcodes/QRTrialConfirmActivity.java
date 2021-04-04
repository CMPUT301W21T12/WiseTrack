package com.faanggang.wisetrack.view.qrcodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExecuteTrialController;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.model.executeTrial.BinomialTrial;
import com.faanggang.wisetrack.model.executeTrial.CountTrial;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
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
    private int trialType;
    private long trialResult;
    private Boolean needsGeolocation;

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
        Trial trial;
        Map trialMap;
        
        if (trialType==0|| trialType==2) {
            trial = new CountTrial((int) trialResult,
                    "",
                    WiseTrackApplication.getCurrentUser().getUserID(),
                    new Date());
            trialMap = trialController.createTrialDocument((CountTrial) trial);
            trialController.executeTrial(trialMap);
            finish();
        } else if (trialType==1){
            trial = new BinomialTrial((int) trialResult,
                    "",
                    WiseTrackApplication.getCurrentUser().getUserID(),
                    new Date());
            trialMap = trialController.createTrialDocument((BinomialTrial) trial);
            trialController.executeTrial(trialMap);
            finish();
        }

    }
    private void cancelTrial(){
        finish();
    }


    private void setTextDetails(String expID, long result) {
        experimentManager.getExperimentInfo(expID, task ->{
            DocumentSnapshot doc = task.getResult();
            if (doc.exists()){
                expNameView.setText(doc.getString("name"));
                trialResultView.setText(String.format("%d", result));
                needsGeolocation = doc.getBoolean("geolocation");
                geoLocationView.setText("GeoLocation Required: " + needsGeolocation.toString());
                trialType =  doc.getLong("trialType").intValue();
            }
        });
    }


}