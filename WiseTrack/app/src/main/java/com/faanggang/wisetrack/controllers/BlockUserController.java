package com.faanggang.wisetrack.controllers;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class controls the creation and storing process of BlockedExperimenters sub-collection inside
 * Experiments collection which stores a list of blocked experimenter.
 * Blocked experimenters are restricted from executing trials of the current experiment.
 */
public class BlockUserController {
    private FirebaseFirestore db;

    public BlockUserController() {
        this.db = FirebaseFirestore.getInstance();
    }

    public void addBlockedUser(String conductorID, String expID) {
        db.collection("Experiments").document(expID).get()
                .addOnCompleteListener(task ->{
                    if (task.isSuccessful()) {
                        ArrayList<String> blockedUsers = (ArrayList<String>) task.getResult().get("blockedUsers");
                        if (!blockedUsers.contains(conductorID)){ // if the user is not already blocked, add it to list
                            HashMap<String,Object> map = new HashMap<>();
                            blockedUsers.add(conductorID);
                            map.put("blockedUsers", blockedUsers);
                            db.collection("Experiments").document(expID).update(map);
                        }
                    }
                });
    }
}
