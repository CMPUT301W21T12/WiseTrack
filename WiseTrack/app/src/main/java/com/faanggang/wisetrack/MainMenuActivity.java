package com.faanggang.wisetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.faanggang.wisetrack.search.SearchActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity {

    ListView experimentList;
    ArrayAdapter<Experiment> experimentAdapter;
    ArrayList<Experiment> experimentDataList;
    Button experimentSearchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Button viewProfileButton = findViewById(R.id.menuProfile_Button);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ViewSelfActivity.class);
                startActivity(intent);
            }
        });


        Button publishButton = findViewById(R.id.menuPublish_button);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, PublishExperimentActivity.class);
                startActivity(intent);
            }
        });

        experimentSearchButton = findViewById(R.id.menuSearch_button);
        experimentSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });


    }
}
