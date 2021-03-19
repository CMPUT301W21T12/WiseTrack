package com.faanggang.wisetrack.model;

import android.app.Application;

import com.faanggang.wisetrack.model.user.Users;

public class WiseTrackApplication extends Application {

    transient private static Users currentUser = null;

    public static void setCurrentUser(Users user) {
        currentUser = user;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }

}
