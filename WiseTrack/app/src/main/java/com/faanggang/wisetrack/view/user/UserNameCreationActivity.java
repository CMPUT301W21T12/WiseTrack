package com.faanggang.wisetrack.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.view.MainMenuActivity;
import com.faanggang.wisetrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Activity that handles username creation for all new users
 */
public class UserNameCreationActivity extends AppCompatActivity {
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
                Log.d("Test", "it got here");
                // query for all user document with the username input by the new user
                usersRef.whereEqualTo("userName", username)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    Log.d("test2", "task is successful");
                                    // username entered by user is unique and does not exists
                                    if (task.getResult().size() == 0) {
                                        userManager.createNewUser(userManager, username);
                                        Log.d("Username creation", "Username created");
                                        Intent intent = new Intent(UserNameCreationActivity.this, MainMenuActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                    else {
                                        //document exists with the user entered username
                                        Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_LONG).show();
                                        Log.d("username creation", "Username already exists error");
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
}
