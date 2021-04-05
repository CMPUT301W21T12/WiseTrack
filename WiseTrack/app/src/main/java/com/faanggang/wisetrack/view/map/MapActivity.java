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

        Intent intent = getIntent();
        String experimentId = intent.getStringExtra("EXP_ID");


        map = findViewById(R.id.mapview);
        Configuration.getInstance().setUserAgentValue(this.getPackageName());

        //map.getOverlays().add(this.myLocationNewOverlay);
        //GeoPoint mL = myLocationNewOverlay.getMyLocation();
        map.getController().setZoom(7.0);
        if (geolocationManager.getLastLocation() != null) {
            GeoPoint location = new GeoPoint(geolocationManager.getLastLocation().getLatitude(), geolocationManager.getLastLocation().getLongitude());
            renderLocation(location);
        } else {
            map.scrollTo(0, 0);
        }
        //trialFetchManager = new TrialFetchManager(firebaseFirestore, this);
        //trialFetchManager.fetchTrials("");
    }


    public void placeTrials(ArrayList<Trial> trials) {
        for (Trial trial: trials) {
            Marker trialMarker = new Marker(map);
            //trialMarker.setPosition(trial.getTrialGeolocation());
            trialMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(trialMarker);
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
