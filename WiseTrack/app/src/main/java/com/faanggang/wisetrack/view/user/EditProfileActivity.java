package com.faanggang.wisetrack.view.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.controllers.UserManager;
import com.faanggang.wisetrack.model.user.Users;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * Activity that handles user edit to their profile
 */
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

        populateText(editFirstName, editLastName, editPhoneNumber, editEmail);

        Button confirmEditButton = findViewById(R.id.confirmEditButton);
        confirmEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateUserInfoChanges(currentUser, editFirstName, editLastName, editPhoneNumber, editEmail);
                userManager.updateCurrentUser(currentUser.getUserID());
                Intent intent = new Intent(EditProfileActivity.this, ViewSelfActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * method that handles user input on GUI updates currentUser info using
     * currentUser stored in WiseTrackApplication
     * @param currentUser
     * currentUser stored in WiseTrackApplication
     * @param editFirstName
     * new First Name input by user
     * @param editLastName
     * new Last Name input by user
     * @param editPhoneNumber
     * new phone number input by user
     * @param editEmail
     * new email input by user
     */
    public void updateUserInfoChanges(Users currentUser ,EditText editFirstName, EditText editLastName, EditText editPhoneNumber,
                                      EditText editEmail) {

        String firstName = editFirstName.getText().toString();
        String lastName = editLastName.getText().toString();
        String phoneNumber = editPhoneNumber.getText().toString();
        String email = editEmail.getText().toString();

        currentUser.setFirstName(firstName);
        currentUser.setLastName(lastName);
        currentUser.setPhoneNumber(phoneNumber);
        currentUser.setEmail(email);
    }

    /**
     * method to populate the edit text with current current user info
     * @param editFirstName
     * EditText for first name
     * @param editLastName
     * EditText for last name
     * @param editPhoneNumber
     * EditText for phone number
     * @param editEmail
     * EditText for email
     */
    public void populateText(EditText editFirstName, EditText editLastName, EditText editPhoneNumber,
                             EditText editEmail) {

        Users currentUser = WiseTrackApplication.getCurrentUser();

        editFirstName.setText(currentUser.getFirstName());
        editLastName.setText(currentUser.getLastName());
        editPhoneNumber.setText(currentUser.getPhoneNumber());
        editEmail.setText(currentUser.getEmail());
    }
}
