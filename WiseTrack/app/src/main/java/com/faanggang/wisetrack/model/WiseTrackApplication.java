package com.faanggang.wisetrack.model;

import android.app.Application;


import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.model.user.Users;

import java.util.ArrayList;

public class WiseTrackApplication extends Application {

    transient private static Users currentUser = null;
    transient private static Experiment currentExperiment = null;
    transient private static ArrayList<String> userSubscriptions = new ArrayList<String>();

    public static void setCurrentUser(Users user) {
        currentUser = user;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }

    public static void setUserSubscriptions(ArrayList<String> subscriptions){
        userSubscriptions = subscriptions;
    }
  
    public static ArrayList<String> getUserSubscriptions(){
        return userSubscriptions;
    }
}
