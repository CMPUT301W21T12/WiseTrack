package com.faanggang.wisetrack.view.user;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.model.user.Users;

/**
 * Activity that displays user's own personal information
 */
public class ViewSelfActivity extends AppCompatActivity {
    private TextView userNameText;
    private TextView firstNameText;
    private TextView lastNameText;
    private TextView phoneNumberText;
    private TextView emailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_self_profile);

        Users currentUser = WiseTrackApplication.getCurrentUser();

        userNameText = findViewById(R.id.view_userName);
        firstNameText = findViewById(R.id.view_fName);
        lastNameText = findViewById(R.id.view_lastName);
        phoneNumberText = findViewById(R.id.view_phoneNumber);
        emailText = findViewById(R.id.view_Email);

        setText(currentUser);

        Button editButton = findViewById(R.id.view_editProfile);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewSelfActivity.this, EditProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final Button getUserIDButton = findViewById(R.id.get_UserID_Button);
        getUserIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = WiseTrackApplication.getCurrentUser().getUserID();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData idClip = ClipData.newPlainText("id", userID);
                clipboard.setPrimaryClip(idClip);
                String string = "UserID: "+userID+ " has been copied to your clipboard";
                Toast.makeText(getApplicationContext(),string, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void setText(Users currentUser) {
        userNameText.setText(currentUser.getUserName());
        firstNameText.setText(currentUser.getFirstName());
        lastNameText.setText(currentUser.getLastName());
        phoneNumberText.setText(currentUser.getPhoneNumber());
        emailText.setText(currentUser.getEmail());
    }




}
