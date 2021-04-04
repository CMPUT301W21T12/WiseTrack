package com.faanggang.wisetrack.model;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.faanggang.wisetrack.model.user.Users;

public class WiseTrackApplication extends Application {

    transient private static Users currentUser = null;

    public static void setCurrentUser(Users user) {
        currentUser = user;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }

    public static Boolean getLocationPermission(Context context){
        if (ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else{
            return false;
        }
    }
}
