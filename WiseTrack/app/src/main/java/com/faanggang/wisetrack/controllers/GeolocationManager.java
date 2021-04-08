package com.faanggang.wisetrack.controllers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.faanggang.wisetrack.view.MainMenuActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.function.Function;

/**
 * GeolocationManager is a singleton controlling the fetching of the user's geolocation.
 */
public class GeolocationManager {
    // many methods are adapter from the ones found on the following webpage
    // https://developer.android.com/training/location/request-updates
    // these are licensed under Apache 2.0 https://www.apache.org/licenses/LICENSE-2.0
    private static Context context;
    private FusedLocationProviderClient fusedLocationClient;
    private static GeolocationManager geolocationManager = null;
    private static boolean activated = false;
    private static Location lastLocation = null;

    /**
     * getInstance provides the static instance of this singleton supplied with the context
     * of the activity using the class.
     * @param context
     * context is the Context of the activity using this manager
     * @return
     */
    public static GeolocationManager getInstance(Context context) {
        if (geolocationManager == null) {
            geolocationManager = new GeolocationManager(context);
        }
        return geolocationManager;
    }

    /**
     * stopLocationUpdates stops location updates.
     */
    public void stopLocationUpdate() {
        fusedLocationClient.removeLocationUpdates(new LocationCallback() {});
    }

    /**
     * This private constructor initializes the static Singleton with the context.
     * @param context
     * context is the Context of the Activity that first called getInstance on the Singleton
     */
    private GeolocationManager(Context context) {
        this.context = context;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }

    /**
     * setContext allows for the context of the Singleton to be change when in use by different
     * activities
     * @param context
     * context is the Context of the Activity last using.
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * startLocationUpdates starts location updates
     */
    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        activated = true;
        fusedLocationClient.requestLocationUpdates(LocationRequest
                        .create().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000),
                new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        Log.w("Last Location", locationResult.getLastLocation().toString());
                        lastLocation = locationResult.getLastLocation();
                    }
                },
                Looper.getMainLooper());
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public boolean isActivated() {
        return activated;
    }

    /**
     * promptPermissions prompts the user of the passed activity to accept Location permissions
     * @param activity
     * activity is the Activity wherein the user is to be prompted to accept permissions.
     */
    public void promptPermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,},1);
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,},2);
        }
    }
}

