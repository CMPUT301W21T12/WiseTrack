package com.faanggang.wisetrack.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.controllers.ConnectionManager;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.view.MainMenuActivity;
import com.faanggang.wisetrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Activity that handles username creation for all new users
 */
public class UserNameCreationActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    UserManager userManager = new UserManager(db);
    ConnectionManager wifiConMgr = new ConnectionManager(this);
    private boolean networkConnected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.username_creation);

        EditText editUserName = findViewById(R.id.editUserName);

        Button confirmButton = findViewById(R.id.userNameConfirmButton);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                networkConnected = wifiConMgr.getInternetConnection();
                if (!networkConnected) {
                    Toast.makeText(getApplicationContext(),"Please connect to internet to create a new username", Toast.LENGTH_LONG).show();
                }
                else {
                    String username = editUserName.getText().toString();
                    if (detectSpecial(username)) {
                        Toast.makeText(getApplicationContext(), "Username cannot contain special characters", Toast.LENGTH_LONG).show();
                    }
                    else if (username.length() > 13) {
                        Toast.makeText(getApplicationContext(), "Username cannot contain more than 13 characters", Toast.LENGTH_LONG).show();
                    }
                    else {
                        CollectionReference usersRef = db.collection("Users");
                        // query for all user document with the username input by the new user
                        usersRef.whereEqualTo("userName", username)
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            Log.d("test2", "task is successful");
                                            // username entered by user is unique and does not exists
                                            if (task.getResult().size() == 0) {
                                                userManager.createNewUser(userManager, username);
                                                Log.d("Username creation", "Username created");
                                                Intent intent = new Intent(UserNameCreationActivity.this, MainMenuActivity.class);
                                                SystemClock.sleep(100);
                                                startActivity(intent);
                                            } else {
                                                //document exists with the user entered username
                                                Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_LONG).show();
                                                Log.d("username creation", "Username already exists error");
                                            }
                                        } else {
                                            // error retrieving document
                                            Log.d("username creation:", "error getting document");
                                        }
                                    }
                                });
                    }
                }
            }
        });
    }

    /**
     * Check if user has entered special characters in username field
     * @param s
     * String of the username entered by user
     * @return
     * returns True is special character is found, else false
     */
    public boolean detectSpecial(String s) {
        // check if first character is a space
        String c = s.substring(0, 1);
        if (c.equals(" ")) {
            return true;
        }
        else {
            Pattern p = Pattern.compile("[^A-Za-z0-9 ]");
            Matcher m = p.matcher(s);
            return m.find();
        }
    }
}
