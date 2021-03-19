package com.faanggang.wisetrack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;



import com.faanggang.wisetrack.user.UserManager;
import com.faanggang.wisetrack.experiment.UserExperimentManager;
import com.faanggang.wisetrack.user.UserNameCreationActivity;
import com.faanggang.wisetrack.user.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;



/**
 * MainActivity is the landing page for to authenticate
 * before they are able to access the applications main features
 *
 * @author Shao-Zhang
 */

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserManager userManager = new UserManager(db);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        ImageView owl = (ImageView) findViewById(R.id.imageView);
        owl.setImageResource(R.drawable.logo);
    }

    @Override
    public void onStart() {
        super.onStart();
        // gets current user from Firebase Authentication
        currentUser = mAuth.getCurrentUser();
    }

    // method for menu button click
    public void menuClick(View view) {
        if(currentUser == null) {
            Intent intent = new Intent(this, UserNameCreationActivity.class);
            startActivity(intent);
        }
        else {
            // user is a existing user
            Log.w("EXISTING USERID", currentUser.getUid());
            userManager.storeCurrentUser(currentUser.getUid(), userManager);
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }

    }

//    public void getIDButton(View view) {
//        if(currentUser == null){
//            createNewUser();
//        } else {
//            Log.w("USERID", currentUser.getUid());
//        }
//
//    }

    /**
     * This is a method which signs a new user in using Firebase Authentication
     * and calls UserManager class to store default user information into cloud database
     */
//    public void createNewUser(){
//        Activity activity = this;
//        mAuth.signInAnonymously()
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("NEW SIGNIN", "success");
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            userManager.addUser(user.getUid());
//                            storeCurrentUser(user.getUid());
//                            Intent intent = new Intent(activity, MainMenuActivity.class);
//                            startActivity(intent);
//                        } else {
//                            Log.w("NEW SIGNIN", "failure");
//                        }
//                    }
//                });
//
//    }

    /**
     * This method retrieves current user info from database using UserManager
     * and stores it into a WiseTrackApplication singleton class
     * @param uid
     */
//    public void storeCurrentUser(String uid) {
//
//        // creates a OnCompleteListner object that is passed into UserManager
//        OnCompleteListener<DocumentSnapshot> storeUser = new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot userDoc = task.getResult();
//                    if (userDoc.exists()) {
//                        Log.d("Retrieved DocumentSnapshot ID:", userDoc.getId());
//                        // creates currentUser object with user info data from database
//                        Users currentUser = new Users(
//                                userDoc.getString("userName"),
//                                userDoc.getString("firstName"),
//                                userDoc.getString("lastName"),
//                                userDoc.getString("email"),
//                                userDoc.getId(),
//                                userDoc.getString("phoneNumber"));
//                        WiseTrackApplication.setCurrentUser(currentUser);
//                        Log.d("ApplicationUser:", WiseTrackApplication.getCurrentUser().getFirstName());
//                    }
//                    else {
//                        Log.d("Failed: ", "No such document");
//                    }
//                }
//            }
//        };
//
//        userManager.getUserInfo(uid, storeUser);
//    }

}
