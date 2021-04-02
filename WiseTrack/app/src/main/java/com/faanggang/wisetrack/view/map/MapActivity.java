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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Intent intent = getIntent();
        String experimentId = intent.getStringExtra("");

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            map = findViewById(R.id.mapview);
            Configuration.getInstance().setUserAgentValue(this.getPackageName());

            this.myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(this), map);
            this.myLocationNewOverlay.enableMyLocation();

            map.getOverlays().add(this.myLocationNewOverlay);
            GeoPoint mL = myLocationNewOverlay.getMyLocation();
            map.getController().setZoom(5.0);
            getCurrentLocation();
        }
        trialFetchManager = new TrialFetchManager(new FirebaseFirestore, this);
        trialFetchManager.fetchTrials("");
    }

    public void placeTrials(ArrayList<Trial> trials) {
        for (Trial trial: trials) {
            Marker trialMarker = new Marker(map);
            trialMarker.setPosition(trial.getTrialGeolocation());
            trialMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
            map.getOverlays().add(trialMarker);
        }
    }

    // method adapted from https://stackoverflow.com/a/55389814
    // author: Rohit Singh
    // licensed under CC BY-SA 4.0
    public void getCurrentLocation() {

        FusedLocationProviderClient locationProviderClient = LocationServices.
                getFusedLocationProviderClient(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationProviderClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {

                        Log.d("Location", location.toString());
                        renderLocation(location);
                    }
                });

    }

    private void renderLocation(Location loc) {
        map.getOverlays().add(this.myLocationNewOverlay);
        map.getController().setCenter(new GeoPoint(loc.getLatitude(), loc.getLongitude()));
        map.getController().setZoom(5.0);
        map.getController().animateTo(new GeoPoint(loc.getLatitude(), loc.getLongitude()));
    }

    @Override
    public void onSuccessfulFetch(ArrayList<Trial> trials) {
        placeTrials(trials);
    }
}
