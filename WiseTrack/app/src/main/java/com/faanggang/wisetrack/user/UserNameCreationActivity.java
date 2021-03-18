package com.faanggang.wisetrack.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.MainMenuActivity;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.WiseTrackApplication;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class UserNameCreationActivity extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserManager userManager = new UserManager(db);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_creation);

        EditText editUserName = findViewById(R.id.editUserName);

        Button confirmButton = findViewById(R.id.userNameConfirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editUserName.getText().toString();
                CollectionReference usersRef = db.collection("Users");
                usersRef.whereEqualTo("user", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for (DocumentSnapshot document : task.getResult()){
                                        if (document.exists()) {
                                            //document exists with the user entered username
                                            Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_LONG).show();
                                            Log.d("username creation:", "Username already exists error");

                                        }
                                        else {
                                            createNewUser();
                                            FirebaseUser user = mAuth.getCurrentUser();
                                            storeCurrentUser(user.getUid());
                                            Log.d("Username creation", "Username created");
                                        }
                                    }
                                }
                                else {
                                    // error retrieving document
                                    Log.d("username creation:", "error getting document");
                                }
                            }
                        });

            }
        });
    }

    public void createNewUser(){
        Activity activity = this;
        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("NEW SIGNIN", "success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userManager.addUser(user.getUid());
                            storeCurrentUser(user.getUid());
                            Intent intent = new Intent(activity, MainMenuActivity.class);
                            startActivity(intent);
                        } else {
                            Log.w("NEW SIGNIN", "failure");
                        }
                    }
                });

    }

    /**
     * This method retrieves current user info from database using UserManager
     * and stores it into a WiseTrackApplication singleton class
     * @param uid
     */
    public void storeCurrentUser(String uid) {

        // creates a OnCompleteListner object that is passed into UserManager
        OnCompleteListener<DocumentSnapshot> storeUser = new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot userDoc = task.getResult();
                    if (userDoc.exists()) {
                        Log.d("Retrieved DocumentSnapshot ID:", userDoc.getId());
                        // creates currentUser object with user info data from database
                        Users currentUser = new Users(
                                userDoc.getString("userName"),
                                userDoc.getString("firstName"),
                                userDoc.getString("lastName"),
                                userDoc.getString("email"),
                                userDoc.getId(),
                                userDoc.getString("phoneNumber"));
                        WiseTrackApplication.setCurrentUser(currentUser);
                        Log.d("ApplicationUser:", WiseTrackApplication.getCurrentUser().getFirstName());
                    }
                    else {
                        Log.d("Failed: ", "No such document");
                    }
                }
            }
        };

        userManager.getUserInfo(uid, storeUser);
    }

}
