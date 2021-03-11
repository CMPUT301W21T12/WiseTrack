package com.faanggang.wisetrack;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ViewSelfActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_self_profile);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        String uid = currentUser.getUid();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        TextView userNameText = findViewById(R.id.view_userName);
        TextView firstNameText = findViewById(R.id.view_fName);
        TextView lastNameText = findViewById(R.id.view_lastName);
        TextView phoneNumberText = findViewById(R.id.view_phoneNumber);
        TextView emailText = findViewById(R.id.view_Email);

        DocumentReference userRef = db.collection("Users").document(uid);
        userRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userDoc = task.getResult();
                    if (userDoc.exists()) {
                        Log.d("Retrieved DocumentSnapshot ID:", userDoc.getId());
                        userNameText.setText(userDoc.getString("userName"));
                    }
                    else {
                        Log.d("Failed: ", "No such document");
                    }
                }
            }
        });


    }

}
