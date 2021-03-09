package com.faanggang.wisetrack;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.userauth.NewUserActivity;
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
        if(currentUser == null){
            Intent intent = new Intent(this, NewUserActivity.class);
            startActivity(intent);
        } else {
            Log.w("USERID", currentUser.getUid());
        }

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void getIDButton(View view) {
        if(currentUser == null){
            Intent intent = new Intent(this, NewUserActivity.class);
            startActivity(intent);
        } else {
            Log.w("USERID", currentUser.getUid());
        }

    }
}
