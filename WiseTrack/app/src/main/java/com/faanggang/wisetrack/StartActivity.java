package com.faanggang.wisetrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        mAuth = FirebaseAuth.getInstance();

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
            Log.w("USERID", currentUser.getUid());
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void getIDButton(View view) {
        if(currentUser == null){
            createNewUser();
        } else {
            Log.w("USERID", currentUser.getUid());
        }

    }

    public void createNewUser(){
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("SIGNIN", "success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.w("SIGNIN", "failure");
                        }
                    }
                });
    }
}
