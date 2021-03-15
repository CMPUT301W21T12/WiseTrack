package com.faanggang.wisetrack.publish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.faanggang.wisetrack.ErrorMessageActivity;
import com.faanggang.wisetrack.R;

public class PublishExperiment1_Initialization extends AppCompatActivity {

    private EditText inputName;
    private EditText inputDescription;
    private EditText inputRegion;
    private EditText inputMinTrials;
    private Button nextButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_experiment_initialization);

        inputName = findViewById(R.id.name_input);
        inputDescription = findViewById(R.id.description_input);
        inputRegion = findViewById(R.id.region_input);
        inputMinTrials = findViewById(R.id.minTrials_input);

        nextButton = findViewById(R.id.choose_test_type_button);

        // Attaching OnClick listener to the submit button
        nextButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name = null, description = null, region = null;
                int minTrials = -1;

                name = inputName.getText().toString();
                description = inputDescription.getText().toString();
                region = inputRegion.getText().toString();
                minTrials = Integer.parseInt(inputMinTrials.getText().toString());

                Intent intent;

                if (name == null || description == null || region == null || minTrials == -1){
                    // if no input
                    intent = new Intent(PublishExperiment1_Initialization.this, ErrorMessageActivity.class);

                    intent.putExtra("EXTRA_ERROR", "You cannot leave any fields blank!");

                }
                else{
                    intent = new Intent(PublishExperiment1_Initialization.this, PublishExperiment2_TrialType.class);

                    intent.putExtra("EXTRA_NAME", name);
                    intent.putExtra("EXTRA_DESCRIPTION", description);
                    intent.putExtra("EXTRA_REGION", region);
                    intent.putExtra("EXTRA_MIN_TRIALS", minTrials);

                }
                startActivity(intent);

            }
        });

    }

}