package com.faanggang.wisetrack.experiment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.Experiment;
import com.faanggang.wisetrack.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.ArrayList;

public class ViewExperimentActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView expNameView;
    private TextView expDescriptionView;
    private TextView expRegionView;
    private TextView expMinTrialsView;
    private TextView expOwnerView;
    private TextView expStatusView;
    private String expID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expID = getIntent().getStringExtra("EXP_ID");
        Log.w("EXP",expID);
        setContentView(R.layout.view_experiment_detail);
        expNameView = findViewById(R.id.view_experimentName);
        expDescriptionView = findViewById(R.id.view_experimentDescription);
        expRegionView = findViewById(R.id.view_experimentRegion);
        expMinTrialsView = findViewById(R.id.view_min_num_trials);
        expOwnerView = findViewById(R.id.view_owner);
        expStatusView = findViewById(R.id.view_status);

        db.collection("Experiments").document(expID).get()
        .addOnCompleteListener(task ->{
            if (task.isSuccessful()){
                DocumentSnapshot docSnap = task.getResult();
                expNameView.setText(docSnap.getString("name"));
                expDescriptionView.setText(docSnap.getString("description"));
                expRegionView.setText(docSnap.getString("region"));
                expMinTrialsView.setText(docSnap.getLong("minTrials").toString());
                expOwnerView.setText(docSnap.getString("ownerID"));
                //expStatusView.setText(docSnap.getString("status"));
            }
        })
        ;

        FloatingActionButton ExperimentActionMenu = findViewById(R.id.experiment_action_menu);
        ExperimentActionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExperimentActionMenuFragment().show(getSupportFragmentManager(), "experiment action menu");
            }
        });
    }
}
