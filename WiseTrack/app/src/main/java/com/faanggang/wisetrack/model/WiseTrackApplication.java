package com.faanggang.wisetrack.model;

import android.app.Application;


import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.model.user.Users;

public class WiseTrackApplication extends Application {

    transient private static Users currentUser = null;
    transient private static Experiment currentExperiment = null;
    transient private static boolean internetConnection;

    public static void setCurrentUser(Users user) {
        currentUser = user;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }


    public static boolean getWifiConnection() {
        return internetConnection;
    }

    public static void setInternetConnection(boolean connection) {
        internetConnection = connection;
    }
}
