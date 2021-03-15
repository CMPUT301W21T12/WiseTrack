package com.faanggang.wisetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.faanggang.wisetrack.publish.PublishExperiment1_Initialization;

public class ErrorMessageActivity extends AppCompatActivity {

    private TextView errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_message);

        errorMessage = findViewById(R.id.errorMessageTextView);

        Bundle extras = getIntent().getExtras();

        if (extras != null){
            String message = extras.getString("EXTRA_ERROR");
            errorMessage.setText(message);
        }

        // Go back to main
        Intent intent = new Intent(ErrorMessageActivity.this, MainActivity.class);
        startActivity(intent);


    }
}