package com.faanggang.wisetrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PublishExperimentActivity3 extends AppCompatActivity {

    private Button yes;
    private Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_experiment3);

        Intent intent = getIntent();

        yes = findViewById(R.id.yes_button);
        no = findViewById(R.id.no_button);

        yes.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            //    Intent nextIntent = new Intent(PublishExperimentActivity3.this, PublishExperimentActivity4.class);
            //    startActivity(nextIntent);

            }
        });

        no.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

            //    Intent nextIntent = new Intent(PublishExperimentActivity3.this, PublishExperimentActivity4.class);
            //    startActivity(nextIntent);

            }
        });
    }
}