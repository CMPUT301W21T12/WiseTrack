package com.faanggang.wisetrack.user;

import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.WiseTrackApplication;
import com.google.firebase.firestore.auth.User;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

        Users currentUser = WiseTrackApplication.getCurrentUser();

        EditText editFirstName = findViewById(R.id.editTextFirstName);
        EditText editLastName = findViewById(R.id.editTextLastName);
        EditText editPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        EditText editEmail = findViewById(R.id.editTextPhoneNumber);
        EditText editUserName = findViewById(R.id.editTextUserName);

        editFirstName.setText(currentUser.getFirstName());
        editLastName.setText(currentUser.getLastName());
        editPhoneNumber.setText(currentUser.getPhoneNumber());
        editEmail.setText(currentUser.getEmail());
        editUserName.setText(currentUser.getUserName());




    }
}
