package com.faanggang.wisetrack.view.qrcodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.QRCodeManager;
import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.faanggang.wisetrack.view.trial.ExecuteBinomialActivity;

public class SelectBarcodeResultActivity extends AppCompatActivity {
    private TextView title;
    private TextView countResultView;
    private EditText nnicResultView;
    private Spinner dropdown;
    ArrayAdapter<String> adapter;
    private QRCodeManager qrManager;
    private Intent intent;
    private Long trialResult;
    private Button confirmButton;
    private Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_barcode_result);
        intent = getIntent();
        title=findViewById(R.id.barcode_exp_title);
        countResultView = findViewById(R.id.barcode_count_result);
        nnicResultView = findViewById(R.id.barcode_NNIC_result);
        dropdown = findViewById(R.id.barcode_binomial_result);
        qrManager = new QRCodeManager();
        confirmButton = findViewById(R.id.barcode_confirm_button);
        cancelButton = findViewById(R.id.barcode_cancel_button);
        setText();
        showCorrectResult();
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.binomial_result_items));
        // set dropdown layout style
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);  // attach data adapter to spinner
        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("")) {
                    // nothing selected
                    trialResult = -1L;  // set default value
                } else {
                    String item = parent.getItemAtPosition(position).toString();
                    if (item.equals("Success")) {
                        Toast.makeText(parent.getContext(), "Selected: Success", Toast.LENGTH_SHORT).show();
                        trialResult = 1L;
                    } else if (item.equals("Failure")) {
                        Toast.makeText(parent.getContext(), "Selected: Failure", Toast.LENGTH_SHORT).show();
                        trialResult = 0L;
                    } else {
                        try {
                            throw new Exception("No valid spinner item selection detected.");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Warning: No trial result selected!", Toast.LENGTH_SHORT).show();
                // invokes automatic callback interface
            }
        });

        confirmButton = findViewById(R.id.barcode_confirm_button);
        cancelButton = findViewById(R.id.barcode_cancel_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmClick();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelClick();
            }
        });
    }

    private void setText() {
        title.setText(intent.getStringExtra("EXP_TITLE"));
    }

    private void showCorrectResult() {
        Long trialType = intent.getLongExtra("EXP_TYPE", -1);
        countResultView.setText(trialType.toString());
        switch (trialType.intValue()) {
            case 0:
                nnicResultView.setVisibility(View.GONE);
                dropdown.setVisibility(View.GONE);
                countResultView.setText("Result: Add 1 Count");
                break;
            case 1:
                nnicResultView.setVisibility(View.GONE);
                countResultView.setVisibility(View.GONE);
                break;
            case 2:
                countResultView.setVisibility(View.GONE);
                dropdown.setVisibility(View.GONE);
                break;
        }
    }

    private void confirmClick() {
        Long trialType = intent.getLongExtra("EXP_TYPE", -1);
        switch (trialType.intValue()) {
            case 0:
                trialResult = 1L;
                break;
            case 2:
                trialResult = Long.parseLong(nnicResultView.getText().toString());
                break;
        }
        if (trialResult<0){
            Toast.makeText(getApplicationContext(), "Invalid Selection!\n", Toast.LENGTH_SHORT).show();
        } else{
            String expID = intent.getStringExtra("EXP_ID");
            String barcode = intent.getStringExtra("BARCODE");
            qrManager.addBarcode(expID, trialResult.intValue(), barcode, WiseTrackApplication.getCurrentUser().getUserID());
            finish();
        }
    }
    private void cancelClick(){
        finish();
    }
}