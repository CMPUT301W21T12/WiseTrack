package com.faanggang.wisetrack.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.model.WiseTrackApplication;

import com.faanggang.wisetrack.controllers.GeolocationManager;

import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.view.experiment.MyExperimentActivity;
import com.faanggang.wisetrack.view.experiment.MySubscriptionActivity;
import com.faanggang.wisetrack.view.publish.PublishExperiment1_Initialization;
import com.faanggang.wisetrack.view.qrcodes.CameraScannerActivity;
import com.faanggang.wisetrack.view.qrcodes.ViewQRCodeActivity;
import com.faanggang.wisetrack.view.search.SearchActivity;
import com.faanggang.wisetrack.view.user.ViewSelfActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

import java.util.ArrayList;

/**
 * MainMenuActivity displays all available options for user(s)
 * to navigate to
 */

public class MainMenuActivity extends AppCompatActivity {

    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Button experimentSearchButton;
    LocationCallback locationCallback;
    GeolocationManager geolocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        geolocationManager = GeolocationManager.getInstance(this);
        geolocationManager.setContext(this);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
        }

        final Button viewProfileButton = findViewById(R.id.menuProfile_Button);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ViewSelfActivity.class);
                startActivity(intent);
            }
        });

        final Button publishButton = findViewById(R.id.menuPublish_button);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, PublishExperiment1_Initialization.class);
                startActivity(intent);
            }
        });


        final Button experimentSearchButton = findViewById(R.id.menuSearch_button);


        experimentSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        final Button myExperimentsButton = findViewById(R.id.menuViewExperiments_button);

        myExperimentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, MyExperimentActivity.class);
                startActivity(intent);
            }
        });

        final Button mySubscriptionsButton = findViewById(R.id.menuViewSubscriptions_button);
        mySubscriptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, MySubscriptionActivity.class);
                startActivity(intent);
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            geolocationManager.startLocationUpdates();
        } else {
            geolocationManager.promptPermissions(this);
        }

        final Button scanQRCodeButton = (Button) findViewById(R.id.menuScanQR_button);

        scanQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(MainMenuActivity.this, CameraScannerActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Please Allow Camera Permissions", Toast.LENGTH_SHORT);
                }

            }
        });
    }

    // adapted from code found https://developer.android.com/training/permissions/requesting
    // which is licensed under Apache 2.0
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    geolocationManager.startLocationUpdates();
                }
                return;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        geolocationManager.stopLocationUpdate();
    }
}
