package com.faanggang.wisetrack.controllers;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.faanggang.wisetrack.model.WiseTrackApplication;

public class WifiConnectionManager {
    Context context;

    public WifiConnectionManager(Context context) {
        this.context = context;
    }

    public boolean getInternetConnection() {
        boolean internetConnection;
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        // If wifi is turned on
        if (wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();

            if (wifiInfo == null) {
                internetConnection = false;
            }
            else {
                if (wifiInfo.getNetworkId() == -1) {
                    internetConnection = false;
                }
                else {
                    internetConnection = true; // Connected to access point
                }
            }
        }
        else {
            internetConnection = false; // Wifi is turned off
        }

        return internetConnection;
    }
}
