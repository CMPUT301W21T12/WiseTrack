package com.faanggang.wisetrack.user;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


public class UserNameCreationActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                                            Toast.makeText(getApplicationContext(), "This username already exists", Toast.LENGTH_LONG).show();
                                            Log.d("username creation:", "Username already exists error");

                                        }
                                        else{
                                            // create user
                                            Log.d("Username creation", "Username created");
                                        }
                                    }
                                }
                            }
                        });

            }
        });



    }
}
