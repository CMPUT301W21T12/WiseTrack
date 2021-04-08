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

public class ConnectionManager {
    Context context;

    public ConnectionManager(Context context) {
        this.context = context;
    }

    public boolean getInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetwork() != null && isInternetAvailable()) {
            return true;
        }
        return false;
    }

    public boolean isInternetAvailable() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }
}
