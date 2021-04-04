package com.faanggang.wisetrack.view.qrcodes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.QRCodeManager;

public class ViewQRCodeActivity extends AppCompatActivity {
    private TextView qrDescription1;
    private TextView qrTitle;
    private TextView qrDescription2;
    private ImageView qrImage1;
    private ImageView qrImage2;
    private QRCodeManager qrCodeManager;
    private Intent intent;

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
        intent = getIntent();
        setQRCodes(intent.getLongExtra("EXP_TYPE", 0), intent.getStringExtra("EXP_ID"));
        setText();
    }


    private void setText() {
        Long type = intent.getLongExtra("EXP_TYPE",0);
        qrTitle.setText(intent.getStringExtra("EXP_TITLE"));
        switch (type.intValue()){
            case 0: // counts
                qrDescription1.setText("Add to Count");
                break;
            case 1: // binomial
                qrDescription1.setText("Add Success");
                qrDescription2.setText("Add Failure");
                break;
            case 2: // NNIC
                qrDescription1.setText("idk");
                break;
        }
    }
    private void setQRCodes(Long type, String expID){

        switch (type.intValue()){
            case 0: // counts
                break;
            case 1: // binomial
                qrCodeManager.requestCodesForExperiment(expID, 1, qrImage1);
                qrCodeManager.requestCodesForExperiment(expID, 0, qrImage2);
                break;
            case 2: // NNIC
                break;
        }
    }



}