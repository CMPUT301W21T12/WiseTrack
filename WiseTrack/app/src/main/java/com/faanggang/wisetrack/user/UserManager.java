package com.faanggang.wisetrack.user;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserManager {

    private FirebaseFirestore db;

    public UserManager(FirebaseFirestore db) {
        this.db = db;
    }

    public void getUserInfo(String uid, OnCompleteListener<DocumentSnapshot> callback) {

        DocumentReference userRef = db.collection("Users").document(uid);
        userRef.get().addOnCompleteListener(callback);

    }
}
