package com.faanggang.wisetrack.user;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import java.util.HashMap;

public class ViewSelfActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private TextView userNameText;
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView phoneNumberText;
    private TextView emailText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_self_profile);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        userNameText = findViewById(R.id.view_userName);
        firstNameText = findViewById(R.id.view_fName);
        lastNameText = findViewById(R.id.view_lastName);
        phoneNumberText = findViewById(R.id.view_phoneNumber);
        emailText = findViewById(R.id.view_Email);

        UserManager manager = new UserManager(db);

        OnCompleteListener<DocumentSnapshot> render = new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userDoc = task.getResult();
                    if (userDoc.exists()) {
                        Log.d("Retrieved DocumentSnapshot ID:", userDoc.getId());
                        setText(userDoc);
                    }
                    else {
                        Log.d("Failed: ", "No such document");
                    }
                }
            }
        };

        manager.getUserInfo(uid, render);

    }

    public void setText(DocumentSnapshot userDoc) {
        userNameText.setText(userDoc.getString("userName"));
        firstNameText.setText(userDoc.getString("firstName"));
        lastNameText.setText(userDoc.getString("lastName"));
        phoneNumberText.setText(userDoc.getString("phoneNumber"));
        emailText.setText(userDoc.getString("email"));
    }



}
