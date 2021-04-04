package com.faanggang.wisetrack.view.qrcodes;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.QRCodeManager;

public class ViewQRCodeActivity extends AppCompatActivity implements QRCodeManager.codeRequestListener {
    private TextView qrDescription1;
    private TextView qrDescription2;
    private ImageView qrImage1;
    private ImageView qrImage2;
    private QRCodeManager qrCodeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qr_code);
        qrCodeManager = new QRCodeManager(this);
        qrDescription1 = findViewById(R.id.qr_description1);
        qrDescription2 = findViewById(R.id.qr_description2);
        qrImage1 = findViewById(R.id.qr_code1);
        qrImage2 = findViewById(R.id.qr_code2);
        Bitmap bitmap = qrCodeManager.stringToBitmap("ayoooo", 200, 200);
        qrImage1.setImageBitmap(bitmap);
    }


    @Override
    public void onRequestGet(String code) {

    }
}