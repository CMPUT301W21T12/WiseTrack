package com.faanggang.wisetrack.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.WiseTrackApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

/**
 * Activity that displays user's own personal information
 */
public class ViewSelfActivity extends AppCompatActivity {
    private TextView userNameText;
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView phoneNumberText;
    private TextView emailText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_self_profile);

        Users currentUser = WiseTrackApplication.getCurrentUser();

        userNameText = findViewById(R.id.view_userName);
        firstNameText = findViewById(R.id.view_fName);
        lastNameText = findViewById(R.id.view_lastName);
        phoneNumberText = findViewById(R.id.view_phoneNumber);
        emailText = findViewById(R.id.view_Email);

        setText(currentUser);

        Button editButton = findViewById(R.id.view_editProfile);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSelfActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setText(Users currentUser) {
        userNameText.setText(currentUser.getUserName());
        firstNameText.setText(currentUser.getFirstName());
        lastNameText.setText(currentUser.getLastName());
        phoneNumberText.setText(currentUser.getPhoneNumber());
        emailText.setText(currentUser.getEmail());
    }


}
