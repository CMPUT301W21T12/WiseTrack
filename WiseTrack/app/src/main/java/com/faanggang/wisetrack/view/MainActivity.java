package com.faanggang.wisetrack.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.controllers.ConnectionManager;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.view.user.UserNameCreationActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseAuth;



/**
 * MainActivity is the landing page for to authenticate
 * before they are able to access the applications main features
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
            //direct new user to username creation activity
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
}
