package com.faanggang.wisetrack.publish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.faanggang.wisetrack.R;

import java.util.EmptyStackException;

public class PublishExperiment3_Finalize extends AppCompatActivity
    implements View.OnClickListener{

    private Button yes;
    private Button no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_experiment_finalize);

        yes = findViewById(R.id.publish3_yes_button);
        no = findViewById(R.id.publish3_no_button);

        yes.setOnClickListener(this);
        no.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(PublishExperiment3_Finalize.this, PublishExperiment4_Complete.class);
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