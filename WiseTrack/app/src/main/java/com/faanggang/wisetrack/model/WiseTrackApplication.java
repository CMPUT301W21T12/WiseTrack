package com.faanggang.wisetrack.model;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.model.user.Users;

public class WiseTrackApplication extends Application {

    transient private static Users currentUser = null;
    transient private static Experiment currentExperiment = null;
    transient private static Boolean internetConnection = null;

    public static void setCurrentUser(Users user) {
        currentUser = user;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }

    public Boolean getInternetConnection() {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

        // If wifi is turned on
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            if (wifiInfo == null) {
                return false;
            }
            else {
                if (wifiInfo.getNetworkId() == -1) {
                    return false; // Not connected to an access point
                }
                return true; // Connected to access point
            }
        }
        else {
            return false; // Wifi is turned off
        }
    }
}
