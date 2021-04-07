package com.faanggang.wisetrack.view.publish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.faanggang.wisetrack.R;

/**
 * PublishExperiment1_Initialization Activity:
 * Gets user input for experiment name, description, region, and minimum # of trials
 */
public class PublishExperiment1_Initialization extends AppCompatActivity {

    private EditText inputName;
    private EditText inputDescription;
    private EditText inputRegion;
    private EditText inputMinTrials;
    private Button nextButton;
    private String name;
    private String description;
    private String region;
    private String minTrials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publish_experiment_initialization);

        inputName = findViewById(R.id.name_input);
        inputDescription = findViewById(R.id.description_input);
        inputRegion = findViewById(R.id.region_input);
        inputMinTrials = findViewById(R.id.minTrials_input);
        nextButton = findViewById(R.id.choose_test_type_button);

        inputName.addTextChangedListener(inputTextWatcher);
        inputDescription.addTextChangedListener(inputTextWatcher);
        inputRegion.addTextChangedListener(inputTextWatcher);
        inputMinTrials.addTextChangedListener(inputTextWatcher);



    }

    private TextWatcher inputTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            name = inputName.getText().toString();
            description = inputDescription.getText().toString();
            region = inputRegion.getText().toString();
            minTrials = inputMinTrials.getText().toString();

            nextButton.setEnabled(!name.isEmpty() && !description.isEmpty() && !region.isEmpty()
                    && !minTrials.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

            // Attaching OnClick listener to the submit button
            nextButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    int intMinTrials = Integer.parseInt(minTrials);

                    Intent intent;

                    intent = new Intent(PublishExperiment1_Initialization.this, PublishExperiment2_TrialType.class);

                    intent.putExtra("EXTRA_NAME", name);
                    intent.putExtra("EXTRA_DESCRIPTION", description);
                    intent.putExtra("EXTRA_REGION", region);
                    intent.putExtra("EXTRA_MIN_TRIALS", intMinTrials);

                    startActivity(intent);

                }
            });
        }
    };


}