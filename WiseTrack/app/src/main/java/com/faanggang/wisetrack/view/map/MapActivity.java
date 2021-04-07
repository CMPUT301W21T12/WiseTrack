package com.faanggang.wisetrack.view.map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.GeolocationManager;
import com.faanggang.wisetrack.controllers.TrialFetchManager;
import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements TrialFetchManager.TrialFetcher {
    MapView map;
    MyLocationNewOverlay myLocationNewOverlay;
    TrialFetchManager trialFetchManager;
    FirebaseFirestore firebaseFirestore;
    GeolocationManager geolocationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        firebaseFirestore = FirebaseFirestore.getInstance();
        geolocationManager = GeolocationManager.getInstance(this);
        geolocationManager.setContext(this);
        TrialFetchManager trialFetchManager = new TrialFetchManager(this);


        Bundle intent = getIntent().getExtras();
        String experimentId = intent.getString("EXP_ID");
        trialFetchManager.fetchTrials(experimentId);

        map = findViewById(R.id.mapview);
        Configuration.getInstance().setUserAgentValue(this.getPackageName());

        map.getController().setZoom(7.0);
    }


    public void placeTrials(ArrayList<Trial> trials) {
        GeoPoint location = null;
        Marker currentLocationMarker = null;
        for (Trial trial: trials) {
            if (trial.getTrialGeolocation() == null) {
                continue;
            }
            location = new GeoPoint(trial.getTrialGeolocation().getLatitude(), trial.getTrialGeolocation().getLongitude());
            renderLocation(location);
            currentLocationMarker = new Marker(map);
            currentLocationMarker.setOnMarkerClickListener((marker, mapView) -> true);
            currentLocationMarker.setPosition(location);
            currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(currentLocationMarker);
        }
        if (currentLocationMarker != null) {
            map.getController().setCenter(location);
            map.getController().setZoom(5.0);
            map.getController().animateTo(location);
        }
    }

    // method adapted from https://stackoverflow.com/a/55389814
    // author: Rohit Singh
    // licensed under CC BY-SA 4.0

    private void renderLocation(GeoPoint loc) {
        map.getController().setCenter(loc);
        map.getController().setZoom(5.0);
        map.getController().animateTo(loc);
    }

    @Override
    public void onSuccessfulFetch(ArrayList<Trial> trials) {
        placeTrials(trials);
    }
}
