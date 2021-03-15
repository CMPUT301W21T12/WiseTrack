package com.faanggang.wisetrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.faanggang.wisetrack.experiment.MyExperimentActivity;
import com.faanggang.wisetrack.publish.PublishExperimentActivity;
import com.faanggang.wisetrack.search.SearchActivity;
import com.faanggang.wisetrack.user.ViewSelfActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

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


        final Button viewProfileButton = findViewById(R.id.menuProfile_Button);
        viewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ViewSelfActivity.class);
                startActivity(intent);
            }
        });


        final Button publishButton = findViewById(R.id.menuPublish_button);
        publishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuActivity.this, PublishExperimentActivity.class);
                startActivity(intent);
            }
        });


        experimentSearchButton = findViewById(R.id.menuSearch_button);

        final Button experimentSearchButton = findViewById(R.id.menuSearch_button);


        experimentSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, SearchActivity.class);
                startActivity(intent);
            }
        });

        final Button myExperimentsButton = findViewById(R.id.menuViewExperiments_button);

        myExperimentsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, MyExperimentActivity.class);
                startActivity(intent);
            }
    });


    }

}
