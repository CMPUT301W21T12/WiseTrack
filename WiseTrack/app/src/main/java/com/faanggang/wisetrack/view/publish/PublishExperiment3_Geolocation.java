package com.faanggang.wisetrack.view.publish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.faanggang.wisetrack.R;

import java.util.EmptyStackException;

/**
 * PublishExperiment3_Geolocation Activity:
 * Ask user if geolocation is required.
 * If button click `yes` => set geolocation as TRUE.
 * If button click `no` => set geolocation as FALSE.
 */

public class PublishExperiment3_Geolocation extends AppCompatActivity
    implements View.OnClickListener{

    private Button yes;
    private Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_experiment_geolocation);

        yes = findViewById(R.id.publish3_yes_button);
        no = findViewById(R.id.publish3_no_button);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(PublishExperiment3_Geolocation.this, PublishExperiment4_Complete.class);
        Bundle extras = getIntent().getExtras();

        // put in previous extras/variables to the next intent so that the next activity
        // can access them
        if( extras != null)
            intent.putExtras(extras);

        boolean geolocation;

        if (v.getId() == R.id.publish3_yes_button)
            geolocation = true;
        else if (v.getId() == R.id.publish3_no_button)
            geolocation = false;
        else
            throw new EmptyStackException();

        intent.putExtra("EXTRA_GEOLOCATION", geolocation);

        startActivity(intent);

    }

}