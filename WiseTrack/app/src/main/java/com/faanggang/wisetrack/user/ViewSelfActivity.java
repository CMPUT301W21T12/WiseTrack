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

        OnCompleteListener<DocumentSnapshot> renderTextView = new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userDoc = task.getResult();
                    if (userDoc.exists()) {
                        Log.d("Retrieved DocumentSnapshot ID:", userDoc.getId());
                        setText(userDoc);
                        storeCurrentUser(userDoc);
                    }
                    else {
                        Log.d("Failed: ", "No such document");
                    }
                }
            }
        };

        manager.getUserInfo(uid, renderTextView);

        Button editButton = findViewById(R.id.view_editProfile);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSelfActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    public void setText(DocumentSnapshot userDoc) {
        userNameText.setText(userDoc.getString("userName"));
        firstNameText.setText(userDoc.getString("firstName"));
        lastNameText.setText(userDoc.getString("lastName"));
        phoneNumberText.setText(userDoc.getString("phoneNumber"));
        emailText.setText(userDoc.getString("email"));
    }

    public void storeCurrentUser(DocumentSnapshot userDoc) {
        Users currentUser = new Users(userDoc.getString("userName"),
                userDoc.getString("firstName"),
                userDoc.getString("lastName"),
                userDoc.getString("email"),
                userDoc.getId(),
                userDoc.getString("phoneNumber"));
        WiseTrackApplication.setCurrentUser(currentUser);
        Log.d("ApplicationUser:", currentUser.getFirstName());
    }

}
