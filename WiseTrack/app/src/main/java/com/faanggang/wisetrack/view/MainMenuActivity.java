package com.faanggang.wisetrack.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.faanggang.wisetrack.view.experiment.MyExperimentActivity;
import com.faanggang.wisetrack.view.experiment.MySubscriptionActivity;
import com.faanggang.wisetrack.view.publish.PublishExperiment1_Initialization;
import com.faanggang.wisetrack.view.search.SearchActivity;
import com.faanggang.wisetrack.view.user.ViewSelfActivity;

import java.util.ArrayList;

/**
 * MainMenuActivity displays all available options for user(s)
 * to navigate to
 */

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

                Intent intent = new Intent(MainMenuActivity.this, PublishExperiment1_Initialization.class);
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

        final Button mySubscriptionsButton = findViewById(R.id.menuViewSubscriptions_button);
        mySubscriptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this, MySubscriptionActivity.class);
                startActivity(intent);
            }
        });


    }

}
