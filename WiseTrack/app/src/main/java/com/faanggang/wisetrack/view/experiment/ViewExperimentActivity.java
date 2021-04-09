package com.faanggang.wisetrack.view.experiment;


import com.faanggang.wisetrack.controllers.GeolocationManager;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.view.MainActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.view.MainMenuActivity;
import com.faanggang.wisetrack.view.map.MapActivity;
import com.faanggang.wisetrack.view.qrcodes.BarcodeRegisterActivity;
import com.faanggang.wisetrack.view.qrcodes.CameraScannerActivity;
import com.faanggang.wisetrack.view.trial.ExecuteBinomialActivity;
import com.faanggang.wisetrack.view.trial.ExecuteCountActivity;
import com.faanggang.wisetrack.view.trial.ExecuteMeasurementActivity;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.view.comment.ViewAllCommentActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.ExperimentManager;
import com.faanggang.wisetrack.controllers.SubscriptionManager;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.view.MainActivity;
import com.faanggang.wisetrack.view.comment.ViewAllCommentActivity;
import com.faanggang.wisetrack.view.qrcodes.ViewQRCodeActivity;
import com.faanggang.wisetrack.view.stats.ViewExperimentResultsActivity;
import com.faanggang.wisetrack.view.trial.ExecuteBinomialActivity;
import com.faanggang.wisetrack.view.trial.ExecuteCountActivity;
import com.faanggang.wisetrack.view.trial.ExecuteMeasurementActivity;
import com.faanggang.wisetrack.view.trial.ViewTrialsActivity;
import com.faanggang.wisetrack.view.user.ViewOtherActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.views.MapView;

public class ViewExperimentActivity extends AppCompatActivity
    implements EndExperimentFragment.OnFragmentInteractionListener,
        UnpublishExperimentFragment.OnFragmentInteractionListener {
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
    private boolean geolocationRequired;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private GeolocationManager geolocationManager;
    private int anotherTrialType;
    private Location location;
    private boolean startedFetching;
    private Experiment experiment;
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
        experiment = (Experiment) getIntent().getSerializableExtra("EXP_OBJ");
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
        geolocationManager = GeolocationManager.getInstance(this);
        geolocationManager.setContext(this);
        startedFetching = false;

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (!geolocationManager.isActivated()) {
                geolocationManager.startLocationUpdates();
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                Log.w("Geolocation is ", location.toString());
                                ViewExperimentActivity.this.location = location;
                            } else {
                                Log.w("Geolocation is", "null");
                            }
                        }
                    });
        }

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
        if (experiment != null){
            setTextWithObject();
        } else {
            setTextWithFirebase();
        }
    }

    private void setTextWithObject(){
        expNameView.setText(experiment.getName());
        expDescriptionView.setText(experiment.getDescription());
        expRegionView.setText(experiment.getRegion());
        expMinTrialsView.setText(String.valueOf(experiment.getMinTrials()));
        geolocationRequired = experiment.getGeolocation();

        String published;
        if (experiment.isPublished())
            published = "Published";
        else
            published = "Unpublished";

        if (experiment.isOpen()) {
            expStatusView.setText("Open + " + published);
            if (geolocationRequired) warnGeolocation();
        } else {
            expStatusView.setText("Closed + " + published);
        }
        trialType = (long) experiment.getTrialType();
        String trialType_str;
        if (trialType == 0) {
            trialType_str = "Count";
            anotherTrialType = 0;
        } else if (trialType == 1) {
            trialType_str = "Binomial";
            anotherTrialType = 1;
        } else if (trialType == 2) {
            trialType_str = "Non-negative Integer";
            anotherTrialType = 2;
        } else if (trialType == 3) {
            trialType_str = "Measurement";
            anotherTrialType = 3;
        } else {
            trialType_str = "Unknown Unicorn";
            anotherTrialType = -1;  // invalid
        }
        expTrialTypeView.setText(trialType_str);
        userID = experiment.getOwnerID();
        if (experiment.getUsername() != null){
            expOwnerView.setText(experiment.getUsername());
        } else {

            userManager.getUserInfo(userID, task2 -> {
                expOwnerView.setText(task2.getResult().getString("userName"));
            });
        }
    }
    private void setTextWithFirebase(){
        expNameView.setText(experiment.getName());
        expDescriptionView.setText(experiment.getDescription());
        expRegionView.setText(experiment.getRegion());
        expMinTrialsView.setText(String.valueOf(experiment.getMinTrials()));
        geolocationRequired = experiment.getGeolocation();

        String published;
        if (experiment.isPublished())
            published = "Published";
        else
            published = "Unpublished";

        if (experiment.isOpen()) {
            expStatusView.setText("Open + " + published);
            if (geolocationRequired) warnGeolocation();
        } else {
            expStatusView.setText("Closed + " + published);
        }
        trialType = (long) experiment.getTrialType();
        String trialType_str;
        if (trialType == 0) {
            trialType_str = "Count";
            anotherTrialType = 0;
        } else if (trialType == 1) {
            trialType_str = "Binomial";
            anotherTrialType = 1;
        } else if (trialType == 2) {
            trialType_str = "Non-Negative Integer";
            anotherTrialType = 2;
        } else if (trialType == 3) {
            trialType_str = "Measurement";
            anotherTrialType = 3;
        } else {
            trialType_str = "Unknown Unicorn";
            anotherTrialType = -1;  // invalid
        }
        expTrialTypeView.setText(trialType_str);
        if (experiment.getUsername() != null){
            expOwnerView.setText(experiment.getUsername());
        } else {
            userID = experiment.getOwnerID();
            userManager.getUserInfo(userID, task2->{
                expOwnerView.setText(task2.getResult().getString("userName"));
            });
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        geolocationManager.stopLocationUpdate();
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

                UnpublishExperimentFragment unpublish_frag = new UnpublishExperimentFragment();
                unpublish_frag.show(getSupportFragmentManager(), "UNPUBLISH_EXPERIMENT");

                return true;
            case R.id.end_experiment_option:
                Toast.makeText(this, "End experiment option selected", Toast.LENGTH_SHORT).show();

                EndExperimentFragment end_frag = new EndExperimentFragment();
                end_frag.show(getSupportFragmentManager(), "END_EXPERIMENT");

                return true;
            case R.id.results_option:
                // For statistics
                Intent statIntent = new Intent( ViewExperimentActivity.this, ViewExperimentResultsActivity.class);
                statIntent.putExtra("EXP_ID", expID);
                startActivity(statIntent);
                return true;
            case R.id.geolocations_option:
                Intent geolocationIntent = new Intent(this, MapActivity.class);
                geolocationIntent.putExtra("EXP_ID", expID);
                startActivity(geolocationIntent);
                return true;
            case R.id.execute_trials_option:
                if (geolocationRequired) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED
                            ||  ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Please allow this app to access Geolocation to proceed", Toast.LENGTH_SHORT).show();
                    } else if (geolocationManager.getLastLocation() == null) {
                        Toast.makeText(this, "Getting user location...", Toast.LENGTH_SHORT).show();
                    } else {
                        selectExecute();
                    }
                } else {
                    selectExecute();

                }
                return true;
            case R.id.comment_option:
                Intent intent = new Intent(ViewExperimentActivity.this, ViewAllCommentActivity.class);
                intent.putExtra("EXP_ID", expID);
                startActivity(intent);
                return true;
            case R.id.view_trials_option:
                Intent viewTrialIntent = new Intent(this, ViewTrialsActivity.class);
                viewTrialIntent.putExtra("EXP_ID", expID);
                viewTrialIntent.putExtra("EXP_TYPE", trialType);
                startActivity(viewTrialIntent);
                return true;
            case R.id.get_qr_option:
                if (trialType !=3) {
                    Intent qrIntent = new Intent(getApplicationContext(), ViewQRCodeActivity.class);
                    qrIntent.putExtra("EXP_ID", expID);
                    qrIntent.putExtra("EXP_TYPE", trialType);
                    qrIntent.putExtra("EXP_TITLE",expNameView.getText());
                    startActivity(qrIntent);
                }
                else {
                    Toast.makeText(this, "No QR Codes for Measurement Experiments", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.register_barcode_option:
                if (trialType !=3) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        Intent qrIntent = new Intent(getApplicationContext(), BarcodeRegisterActivity.class);
                        qrIntent.putExtra("EXP_ID", expID);
                        qrIntent.putExtra("EXP_TYPE", trialType);
                        qrIntent.putExtra("EXP_TITLE",expNameView.getText());
                        startActivity(qrIntent);
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Allow Camera Permissions", Toast.LENGTH_SHORT);
                    }
                }else {
                    Toast.makeText(this, "No QR Codes for Measurement Experiments", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    private boolean selectExecute() {
        Intent executeIntent;

        if (anotherTrialType == 0 || anotherTrialType == 2) {  // handle both count and non-negative integer count trial
            executeIntent = new Intent(ViewExperimentActivity.this, ExecuteCountActivity.class);
            executeIntent.putExtra("EXP_ID", expID);
            executeIntent.putExtra("trialType", anotherTrialType);

        } else if (anotherTrialType == 1) {  // handle binomial trial
            executeIntent = new Intent(ViewExperimentActivity.this, ExecuteBinomialActivity.class);
            executeIntent.putExtra("EXP_ID", expID);
        } else {  // handle measurement trial (3 is only option left)
            executeIntent = new Intent(ViewExperimentActivity.this, ExecuteMeasurementActivity.class);
            executeIntent.putExtra("EXP_ID", expID);
        }

        if (geolocationRequired) {
            executeIntent.putExtra("GEOLOCATION", geolocationManager.getLastLocation());
        }

        startActivity(executeIntent);
        return true;
    }

    @Override
    public void onEndExperimentOk(){
        DocumentReference experimentDB = db.collection("Experiments").document(expID);

        experimentDB
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
        experimentDB.update("keywords", FieldValue.arrayRemove("OPEN"));
        experimentDB.update("keywords", FieldValue.arrayUnion("CLOSED"));

        experiment.setOpen(false);

        // Go back to main
        Intent intent = new Intent(ViewExperimentActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUnpublishExperimentOk() {
        DocumentReference experimentDB = db.collection("Experiments").document(expID);

        experimentDB
                .update("published", false)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("UNPUBLISH_EXPERIMENT", "Experiment " + expID + " successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("UNPUBLISH_EXPERIMENT", "Error updating document", e);

                        // ADD AN ERROR FRAGMENT HERE
                    }
                });

        experiment.setPublished(false);
    }
}
