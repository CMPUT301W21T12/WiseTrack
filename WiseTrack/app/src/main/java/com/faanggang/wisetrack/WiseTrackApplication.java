package com.faanggang.wisetrack;

import android.app.Application;

import com.faanggang.wisetrack.user.Users;

public class WiseTrackApplication extends Application {

    transient private static Users currentUser = null;

    public static void setCurrentUser(Users user) {
        currentUser = user;
    }

    public static Users getCurrentUser() {
        return currentUser;
    }

}
