package com.faanggang.wisetrack.view.experiment;



import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.view.MainActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.view.trial.ExecuteBinomialActivity;
import com.faanggang.wisetrack.view.trial.ExecuteCountActivity;
import com.faanggang.wisetrack.view.trial.ExecuteMeasurementActivity;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.view.comment.ViewAllCommentActivity;

import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.SubscriptionManager;
import com.faanggang.wisetrack.view.stats.ViewExperimentResultsActivity;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.view.user.ViewOtherActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class ViewExperimentActivity extends AppCompatActivity
    implements EndExperimentConfirmFragment.OnFragmentInteractionListener {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private TextView expNameView;
    private TextView expDescriptionView;
    private TextView expRegionView;
    private TextView expMinTrialsView;
    private TextView expOwnerView;
    private TextView expStatusView;
    private TextView expTrialTypeView;
    private Long trialType;  // integer indicator of trial type
    private String expID;
    private String userID;
    private ExperimentManager experimentManager;
    private UserManager userManager;
    private SubscriptionManager subManager;

    private int anotherTrialType;

    public int getAnotherTrialType() {
        return anotherTrialType;
    }

    public void setAnotherTrialType(int anotherTrialType) {
        this.anotherTrialType = anotherTrialType;
    }

    public String getOwnerID() {
        return userID;
    }

    public void setOwnerID(String userID) {
        this.userID = userID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        experimentManager = new ExperimentManager();
        subManager = new SubscriptionManager();
        userManager = new UserManager(FirebaseFirestore.getInstance());
        expID = getIntent().getStringExtra("EXP_ID");
        setContentView(R.layout.view_experiment_detail);
        expNameView = findViewById(R.id.view_experimentName);
        expDescriptionView = findViewById(R.id.view_experimentDescription);
        expRegionView = findViewById(R.id.view_experimentRegion);
        expMinTrialsView = findViewById(R.id.view_min_num_trials);
        expOwnerView = findViewById(R.id.view_owner);
        expStatusView = findViewById(R.id.view_status);
        expTrialTypeView = findViewById(R.id.view_trial_type);
        setText();

        expOwnerView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (userID != null) {
                    Intent intent = new Intent(ViewExperimentActivity.this, ViewOtherActivity.class);
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
            }
        });

        FloatingActionButton ExperimentActionMenu = findViewById(R.id.experiment_action_menu);
        // link floating action button to experiment action menu xml
        registerForContextMenu(ExperimentActionMenu);

        ExperimentActionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.showContextMenu();  // Note: default is long clicking to open action menu
            }
        });
    }

    private void setText(){
        experimentManager.getExperimentInfo(expID, task->{
            DocumentSnapshot docSnap = task.getResult();
            expNameView.setText(docSnap.getString("name"));
            expDescriptionView.setText(docSnap.getString("description"));
            expRegionView.setText(docSnap.getString("region"));
            expMinTrialsView.setText(docSnap.getLong("minTrials").toString());
            if (docSnap.getBoolean("open")) {
                expStatusView.setText("Open");
                if (docSnap.getBoolean("geolocation")){
                    warnGeolocation();
                }
            } else {
                expStatusView.setText("Closed");
            }

            trialType = docSnap.getLong("trialType");
            String trialType_str;
            if (trialType == 0) {
                trialType_str = "Count";
                anotherTrialType = 0;
            } else if (trialType == 1) {
                trialType_str = "Binomial trials";
                anotherTrialType = 1;
            } else if (trialType == 2) {
                trialType_str = "Non-negative integer counts";
                anotherTrialType = 2;
            } else if (trialType == 3) {
                trialType_str = "Measurement trials";
                anotherTrialType = 3;
            } else {
                trialType_str = "Unknown Unicorn";
                anotherTrialType = -1;  // invalid
            }
            expTrialTypeView.setText(trialType_str);
            userID = docSnap.getString("uID");
            userManager.getUserInfo(docSnap.getString("uID"), task2->{
                expOwnerView.setText(task2.getResult().getString("userName"));

            });

        });

    }

    private void warnGeolocation(){
        Toast.makeText(this, "Warning: This experiment requires geolocation!",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.experiment_action_menu_owner, menu);

        // determine which options should be displayed based on whether the current experimenter is the owner
        if (getOwnerID().equals(WiseTrackApplication.getCurrentUser().getUserID())) {
            menu.setGroupVisible(R.id.owner_only_options, true);
        } else if (!getOwnerID().equals(WiseTrackApplication.getCurrentUser().getUserID())) {
            menu.setGroupVisible(R.id.owner_only_options, false);
        }
        menu.setGroupVisible(R.id.experimenter_options, true);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        // check which item was clicked
        switch (item.getItemId()) {
            case R.id.subscribe_option:
                subManager.addSubscription(expID, WiseTrackApplication.getCurrentUser().getUserID());
                return true;  // item clicked return true
            case R.id.unpublish_option:
                Toast.makeText(this, "Unpublish option selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.end_experiment_option:
                Toast.makeText(this, "End experiment option selected", Toast.LENGTH_SHORT).show();

                EndExperimentConfirmFragment frag = new EndExperimentConfirmFragment();
                frag.show(getSupportFragmentManager(), "END_EXPERIMENT");

                return true;
            case R.id.results_option:
                //Toast.makeText(this, "View experiment results selected", Toast.LENGTH_SHORT).show(); // For statistics
                Intent statIntent = new Intent( ViewExperimentActivity.this, ViewExperimentResultsActivity.class);
                statIntent.putExtra("EXP_ID", expID);
                startActivity(statIntent);
                return true;
            case R.id.geolocations_option:
                Toast.makeText(this, "View geolocation option selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.execute_trials_option:
                if (anotherTrialType == 0 || anotherTrialType == 2) {  // handle both count and non-negative integer count trial
                    Intent executeIntent = new Intent(ViewExperimentActivity.this, ExecuteCountActivity.class);
                    executeIntent.putExtra("EXP_ID", expID);
                    executeIntent.putExtra("trialType", anotherTrialType);
                    startActivity(executeIntent);
                    return true;
                } else if (anotherTrialType == 1) {  // handle binomial trial
                    Intent executeIntent = new Intent(ViewExperimentActivity.this, ExecuteBinomialActivity.class);
                    executeIntent.putExtra("EXP_ID", expID);
                    startActivity(executeIntent);
                    return true;
                } else if (anotherTrialType == 3) {  // handle measurement trial
                    Intent executeIntent = new Intent(ViewExperimentActivity.this, ExecuteMeasurementActivity.class);
                    executeIntent.putExtra("EXP_ID", expID);
                    startActivity(executeIntent);
                    return true;
                }
            case R.id.comment_option:
                Intent intent = new Intent(ViewExperimentActivity.this, ViewAllCommentActivity.class);
                intent.putExtra("EXP_ID", expID);
                startActivity(intent);
                return true;
            case R.id.view_trials_option:
                Toast.makeText(this, "View trials option selected", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public void onEndExperimentOk(){
        DocumentReference experiment = db.collection("Experiments").document(expID);

        experiment
                .update("open", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("END_EXPERIMENT", "Experiment " + expID + " successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("END_EXPERIMENT", "Error updating document", e);

                        // ADD AN ERROR FRAGMENT HERE
                    }
                });

        // Update the "OPEN" keyword to "CLOSED"
        experiment.update("keywords", FieldValue.arrayRemove("OPEN"));
        experiment.update("keywords", FieldValue.arrayUnion("CLOSED"));

        // Go back to main
        Intent intent = new Intent(ViewExperimentActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
