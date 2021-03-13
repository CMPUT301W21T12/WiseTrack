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
        currentUser = mAuth.getCurrentUser();
    }

    public void menuClick(View view) {
        if(currentUser == null) {
            createNewUser();
        }
        else {
            Log.w("EXISTING USERID", currentUser.getUid());
            Intent intent = new Intent(this, MainMenuActivity.class);
            startActivity(intent);
        }

    }

    public void getIDButton(View view) {
        if(currentUser == null){
            createNewUser();
        } else {
            Log.w("USERID", currentUser.getUid());
        }

    }

    public void createNewUser(){
        Activity activity = this;
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("SIGNIN", "success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userManager.addUser(user.getUid());
                            Intent intent = new Intent(activity, MainMenuActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w("SIGNIN", "failure");
                        }
                    }
                });

    }

}
