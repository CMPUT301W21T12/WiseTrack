package com.faanggang.wisetrack.view.qrcodes;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.faanggang.wisetrack.R;
import com.faanggang.wisetrack.controllers.QRCodeManager;
import com.google.zxing.Result;

/**
 * This code follows the documentation for the codeScanner library very closely. Only the base functionality for using the camera and scanning is used.
 * All subsequent activity involving QR code handling is code specific to this application.
 * Source: https://github.com/yuriy-budiyev/code-scanner
 * License: MIT
 */
public class CameraScannerActivity extends AppCompatActivity implements QRCodeManager.codeScanListener {
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
                        Log.w("bruh", result.getText());
                        qrManager.readCode(result.getText());

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
    public void onScanValid(String expID, int trialResult) {
        Intent intent = new Intent(getApplicationContext(), QRTrialConfirmActivity.class);
        intent.putExtra("EXP_ID", expID);
        intent.putExtra("TRIAL_RESULT", (long) trialResult);
        startActivity(intent);
    }

    @Override
    public void onScanInvalid() {
        Toast.makeText(getApplicationContext(), "No associated experiment!", Toast.LENGTH_SHORT).show();
    }
}