package com.faanggang.wisetrack.view.qrcodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.QRCodeManager;

import org.w3c.dom.Text;

public class ViewQRCodeActivity extends AppCompatActivity {
    private TextView qrDescription1;
    private TextView qrTitle;
    private TextView qrDescription2;
    private ImageView qrImage1;
    private ImageView qrImage2;
    private QRCodeManager qrCodeManager;
    private Intent intent;
    private Button NNICButton;
    private TextView NNICinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qr_code);
        qrCodeManager = new QRCodeManager();
        qrTitle = findViewById(R.id.qr_title);
        qrDescription1 = findViewById(R.id.qr_description1);
        qrDescription2 = findViewById(R.id.qr_description2);
        qrImage1 = findViewById(R.id.qr_code1);
        qrImage2 = findViewById(R.id.qr_code2);
        NNICButton = findViewById(R.id.qr_NNIC_num_button);
        NNICinput = findViewById(R.id.qr_NNIC_input);
        intent = getIntent();
        setQRCodes(intent.getLongExtra("EXP_TYPE", 0), intent.getStringExtra("EXP_ID"));
        setText();
        NNICButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int input = Integer.parseInt(NNICinput.getText().toString());
                    if (input >=0) {
                        setQRCodesNNIC(input, intent.getStringExtra("EXP_ID"));
                    } else{
                        Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT);
                    }
                } catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    private void setText() {
        Long type = intent.getLongExtra("EXP_TYPE",0);
        qrTitle.setText(intent.getStringExtra("EXP_TITLE"));
        switch (type.intValue()){
            case 0: // counts
                qrDescription1.setText("Add to Count");
                NNICButton.setVisibility(View.GONE);
                NNICinput.setVisibility(View.GONE);
                break;
            case 1: // binomial
                qrDescription1.setText("Add Success");
                qrDescription2.setText("Add Failure");
                NNICButton.setVisibility(View.GONE);
                NNICinput.setVisibility(View.GONE);
                break;
            case 2: // NNIC
                qrDescription1.setText("Input Number Below");
                NNICinput.setText("0");
                break;
        }
    }
    private void setQRCodes(Long type, String expID){

        switch (type.intValue()){
            case 0: // counts
                qrCodeManager.requestCodesForExperiment(expID, 1, qrImage1);
                break;
            case 1: // binomial
                qrCodeManager.requestCodesForExperiment(expID, 0, qrImage1);
                qrCodeManager.requestCodesForExperiment(expID, 1, qrImage2);
                break;

        }
    }
    private void setQRCodesNNIC(int result, String expID){
        qrCodeManager.requestCodesForExperiment(expID, result, qrImage1);
    }


}