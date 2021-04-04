package com.faanggang.wisetrack.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.faanggang.wisetrack.view.MainMenuActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.function.Function;

public class GeolocationManager {
    private static Context context;
    private FusedLocationProviderClient fusedLocationClient;
    private static GeolocationManager geolocationManager = null;
    private static boolean activated = false;

    public static GeolocationManager getInstance(Context context) {
        if (geolocationManager == null) {
            geolocationManager = new GeolocationManager(context);
        }
        return geolocationManager;
    }

    public void stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(new LocationCallback() {});
    }

    private GeolocationManager(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        activated = true;
        fusedLocationClient.requestLocationUpdates(LocationRequest
                        .create().setPriority(LocationRequest.PRIORITY_NO_POWER),
                new LocationCallback() {},
                Looper.getMainLooper());
    }

    public boolean isActivated() {
        return activated;
    }

    public void promptPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
        }
    }
}
