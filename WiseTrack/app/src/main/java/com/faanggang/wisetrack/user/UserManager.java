package com.faanggang.wisetrack.user;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.faanggang.wisetrack.MainMenuActivity;
import com.faanggang.wisetrack.WiseTrackApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class UserManager {

    private FirebaseFirestore db;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public UserManager(FirebaseFirestore db) {
        this.db = db;
    }

    public void getUserInfo(String uid, OnCompleteListener<DocumentSnapshot> callback) {

        DocumentReference userRef = db.collection("Users").document(uid);
        Log.d("getUserInfo", "Successfully retrieved userRef");
        userRef.get().addOnCompleteListener(callback);

    }

    public void addUser(String uid, String username) {
        Map<String, Object> users = new HashMap<>();
        users.put("firstName", "First Name");
        users.put("lastName", "last Name");
        users.put("email", "Email");
        users.put("phoneNumber", "0");
        users.put("userName", username);

        db.collection("Users").document(uid)
                .set(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("Added user", "DocumentSnapshot added with ID: " + uid);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Fail:", "Error writing document");
                    }
                });
    }

    public void updateFireBaseUser(String uid) {
        db.collection("Users").document(uid)
                .update(
                        "email", WiseTrackApplication.getCurrentUser().getEmail(),
                        "firstName", WiseTrackApplication.getCurrentUser().getFirstName(),
                        "lastName", WiseTrackApplication.getCurrentUser().getLastName(),
                        "phoneNumber", WiseTrackApplication.getCurrentUser().getPhoneNumber(),
                        "userName", WiseTrackApplication.getCurrentUser().getUserName()
                );

    }

    public void addExperimentReference(String uid, DocumentReference experimentReference) {
        db.collection("Users").document(uid)
                .get()
                .addOnCompleteListener(task -> {
                    List<DocumentReference> userExperiments = (List<DocumentReference>) task.getResult()
                            .get("createdExperiments");
                    if (userExperiments == null) {
                        userExperiments = new ArrayList<>();
                    }
                    userExperiments.add(experimentReference);
                    db.collection("Users")
                            .document(uid)
                            .update("createdExperiments", userExperiments)
                            .addOnFailureListener(e -> {
                                // perhaps throw something here
                            });
                });
    }

    public void updateExperimentUserNames(String uid, String previousUsername, String newUsername) {
        db.collection("Users").document(uid)
                .get()
                .addOnCompleteListener(outerTask -> {
                    List<DocumentReference> userExperiments = (List<DocumentReference>) outerTask.getResult()
                            .get("createdExperiments");
                    for (DocumentReference dR : userExperiments) {
                        dR.get().addOnCompleteListener(innerTask -> {
                            List<String> keywords = (List<String>) innerTask.getResult()
                                    .get("keywords");
                            keywords.remove(previousUsername.toUpperCase());
                            keywords.add(newUsername.toUpperCase());
                            dR.update("keywords", keywords);
                        });
                    }
                });
    }

    /**
     * This is a method which signs a new user in using Firebase Authentication
     * and calls UserManager class to store default user information into cloud database
     */
    public void createNewUser(UserManager userManager, String username){
        mAuth.signInAnonymously()
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("NEW SIGNIN", "success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userManager.addUser(user.getUid(), username);
                            storeCurrentUser(user.getUid(), userManager);
                        } else {
                            Log.w("NEW SIGNIN", "failure");
                        }
                    }
                });

    }

    /**
     * This method retrieves current user info from database using UserManager
     * and stores it into a WiseTrackApplication singleton class
     * @param uid
     */
    public void storeCurrentUser(String uid, UserManager userManager) {

        // creates a OnCompleteListner object that is passed into UserManager
        OnCompleteListener<DocumentSnapshot> storeUser = new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userDoc = task.getResult();
                    if (userDoc.exists()) {
                        Log.d("Retrieved DocumentSnapshot ID:", userDoc.getId());
                        // creates currentUser object with user info data from database
                        Users currentUser = new Users(
                                userDoc.getString("userName"),
                                userDoc.getString("firstName"),
                                userDoc.getString("lastName"),
                                userDoc.getString("email"),
                                userDoc.getId(),
                                userDoc.getString("phoneNumber"));
                        WiseTrackApplication.setCurrentUser(currentUser);
                        Log.d("ApplicationUser:", WiseTrackApplication.getCurrentUser().getFirstName());
                    }
                    else {
                        Log.d("Failed: ", "No such document");
                    }
                }
            }
        };

        userManager.getUserInfo(uid, storeUser);
    }
}
