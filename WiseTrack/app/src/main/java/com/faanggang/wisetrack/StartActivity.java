package com.faanggang.wisetrack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

    }

    public void menuClick(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        String userID = sharedPref.getString("ID", "");
        Activity current = this;
        // if new user
        if (userID == "") {
            //create new user
            Map<String, Object> user = new HashMap<>();
            user.put("firstName", "First Name");
            user.put("lastName", "last Name");
            user.put("email", "Email");
            user.put("phoneNumber", "0");
            user.put("userName", "User Name");

            db.collection("Users")
                    .add(user)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d("Added user", "DocumentSnapshot added with ID: " + documentReference.getId());
                            editor.putString("ID", documentReference.getId());
                            editor.apply();
                            Intent intent = new Intent(current, MainActivity.class);
                            startActivity(intent);
                        }
                    });
                    //add failure later
        }
        else {
            db.document("Users/" + userID)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        Log.d("get user", document.getId() + " => " + document.getData());
                        Intent intent = new Intent(current, MainActivity.class);
                        startActivity(intent);
                    }


                }
            });

        }

    }
}
