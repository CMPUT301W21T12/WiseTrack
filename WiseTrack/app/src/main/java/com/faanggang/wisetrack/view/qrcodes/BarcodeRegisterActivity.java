package com.faanggang.wisetrack.view.qrcodes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.QRCodeManager;
import com.google.zxing.Result;

//https://github.com/yuriy-budiyev/code-scanner
public class BarcodeRegisterActivity extends AppCompatActivity implements QRCodeManager.barcodeRegisterListener {
    private CodeScanner codeScanner;
    private QRCodeManager qrManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera_scanner);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        qrManager = new QRCodeManager(this);
        codeScanner = new CodeScanner(this, scannerView);
        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        qrManager.checkBarcode(result.getText());

                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        codeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        codeScanner.releaseResources();
        super.onPause();
    }


    @Override
    public void onBarcodeAvailable() {

    }

    @Override
    public void onBarcodeUnavailable() {
        Toast.makeText(getApplicationContext(), "Barcode already in use!", Toast.LENGTH_SHORT).show();
    }
}