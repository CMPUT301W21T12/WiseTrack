package com.faanggang.wisetrack.controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.InetAddresses;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.faanggang.wisetrack.model.WiseTrackApplication;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * ConnectionManager checks if the device is connected to internet
 * and if connection actually works
 */
public class ConnectionManager {
    Context context;

    public ConnectionManager(Context context) {
        this.context = context;
    }

    public boolean getInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetwork() != null) {
            return true;
        }
        return false;
    }
}
