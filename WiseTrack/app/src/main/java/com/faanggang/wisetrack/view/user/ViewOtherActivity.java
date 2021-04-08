package com.faanggang.wisetrack.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.model.user.Users;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewOtherActivity extends AppCompatActivity {
    private TextView userNameText;
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView phoneNumberText;
    private TextView emailText;
    private UserManager userManager;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userManager = new UserManager(FirebaseFirestore.getInstance());
        setContentView(R.layout.view_self_profile);
        intent = getIntent();
        userNameText = findViewById(R.id.view_userName);
        firstNameText = findViewById(R.id.view_fName);
        lastNameText = findViewById(R.id.view_lastName);
        phoneNumberText = findViewById(R.id.view_phoneNumber);
        emailText = findViewById(R.id.view_Email);
        //Log.e("BRUH", intent.getStringExtra("USER_ID"));
        userManager.getUserInfo(intent.getStringExtra("USER_ID"), task -> {
            DocumentSnapshot documentSnapshot = task.getResult();
            setText(documentSnapshot);
        });

        Button editButton = findViewById(R.id.view_editProfile);
        editButton.setVisibility(View.GONE);
        Button userIDButton = findViewById(R.id.get_UserID_Button);
        userIDButton.setVisibility(View.GONE);
    }

    public void setText(DocumentSnapshot docSnap) {
        userNameText.setText(docSnap.getString("userName"));
        firstNameText.setText(docSnap.getString("firstName"));
        lastNameText.setText(docSnap.getString("lastName"));
        phoneNumberText.setText(docSnap.getString("phoneNumber"));
        emailText.setText(docSnap.getString("email"));
    }
}
