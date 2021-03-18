package com.faanggang.wisetrack.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.WiseTrackApplication;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;


public class EditProfileActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    Users currentUser = WiseTrackApplication.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        UserManager userManager = new UserManager(db);

        EditText editFirstName = findViewById(R.id.editTextFirstName);
        EditText editLastName = findViewById(R.id.editTextLastName);
        EditText editPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        EditText editEmail = findViewById(R.id.editTextEmail);
        EditText editUserName = findViewById(R.id.editTextUserName);

        populateText(editFirstName, editLastName, editPhoneNumber, editEmail, editUserName);

        Button confirmEditButton = findViewById(R.id.confirmEditButton);
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserInfoChanges(currentUser, editFirstName, editLastName, editPhoneNumber, editEmail, editUserName);
                userManager.updateFireBaseUser(currentUser.getUserID());
                Intent intent = new Intent(EditProfileActivity.this, ViewSelfActivity.class);
                startActivity(intent);
            }
        });

    }

    public void updateUserInfoChanges(Users currentUser ,EditText editFirstName, EditText editLastName, EditText editPhoneNumber,
                                      EditText editEmail, EditText editUserName) {

        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String phoneNumber = editPhoneNumber.getText().toString();
        String email = editEmail.getText().toString();
        String userName = editUserName.getText().toString();

        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setEmail(email);
        currentUser.setUserName(userName);
    }

    public void populateText(EditText editFirstName, EditText editLastName, EditText editPhoneNumber,
                             EditText editEmail, EditText editUserName) {

        Users currentUser = WiseTrackApplication.getCurrentUser();

        editFirstName.setText(currentUser.getFirstName());
        editLastName.setText(currentUser.getLastName());
        editPhoneNumber.setText(currentUser.getPhoneNumber());
        editEmail.setText(currentUser.getEmail());
        editUserName.setText(currentUser.getUserName());
    }
}
