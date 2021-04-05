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
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.google.firebase.firestore.DocumentSnapshot;

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
                String geolocation = doc.getBoolean("geolocation").toString();
                geoLocationView.setText("GeoLocation Required: " + geolocation);
            }
        });
    }


}