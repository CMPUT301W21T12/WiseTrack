package com.faanggang.wisetrack.controllers;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.faanggang.wisetrack.model.WiseTrackApplication;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class QRCodeManager {
    private FirebaseFirestore db;

    private codeScanListener scanListener;
    private barcodeRegisterListener barcodeListener;

    public interface codeScanListener {
        void onScanValid(String expID, int trialResult);
        void onScanInvalid();
    }

    public interface barcodeRegisterListener {
        void onBarcodeAvailable(String code);
        void onBarcodeUnavailable();
    }


    public QRCodeManager(){
        db = FirebaseFirestore.getInstance();
    }
    public QRCodeManager(codeScanListener listener){
        db = FirebaseFirestore.getInstance();
        this.scanListener = listener;
    }
    public QRCodeManager(barcodeRegisterListener listener){
        db = FirebaseFirestore.getInstance();
        this.barcodeListener = listener;
    }

    public Bitmap stringToBitmap(String str, int qrWidth, int qrHeight) {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, qrWidth, qrHeight, null);
        } catch (IllegalArgumentException | WriterException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, qrWidth, 0, 0, w, h);
        return bitmap;
    }

    public void requestCodesForExperiment(String expID, int trialResult, ImageView image){
        db.collection("QRCodes").whereEqualTo("expID", expID)
            .whereEqualTo("trialResult", trialResult)
            .get().addOnCompleteListener(task ->{
            if (task.isSuccessful()) {
                List<DocumentSnapshot> doc = task.getResult().getDocuments();
                String code;
                if (doc.isEmpty()){
                    code = addQRCode(expID, trialResult);
                } else {
                    code = doc.get(0).getId();
                }
                //PUT REPLACEMENT HERE
                Bitmap bitmap = stringToBitmap(code,200,200);
                image.setImageBitmap(bitmap);
            } else {
                Log.d("QR", "Failed with: ", task.getException());
            }
        });
    }

    public void readCode(String code) {
        db.collection("QRCodes").document(code).get()
            .addOnCompleteListener( task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        Boolean open = doc.getBoolean("isPublic");
                        String onlineID = doc.getString("uID");
                        String currentID = WiseTrackApplication.getCurrentUser().getUserID();
                        Log.w("bruh", onlineID);
                        Log.w("bruh", currentID);
                        if (open || (onlineID.equals(currentID))){
                            String expID = doc.getString("expID");
                            int trialResult = doc.getLong("trialResult").intValue();
                            scanListener.onScanValid(expID, trialResult);
                        } else{
                            scanListener.onScanInvalid();
                        }

                    } else {
                        scanListener.onScanInvalid();
                    }
                } else {
                    Log.d("QR", "Failed with: ", task.getException());
                }
            });
    }

    public void checkBarcode(String code){
        db.collection("QRodes").document(code).get()
            .addOnCompleteListener( task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        barcodeListener.onBarcodeUnavailable();
                    } else{
                        barcodeListener.onBarcodeAvailable(code);
                    }
                } else {
                    Log.d("QR", "Failed with: ", task.getException());
                }
            });
    }

    public void addBarcode(String expID, int trialResult, String id, String userID){
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("expID", expID);
        codeMap.put("trialResult", trialResult);
        codeMap.put("isPublic", false);
        codeMap.put("uID", userID);
        DocumentReference newCode = db.collection("QRCodes").document(id);
        newCode.set(codeMap);
    }

    private String addQRCode(String expID, int trialResult){
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("expID", expID);
        codeMap.put("trialResult", trialResult);
        codeMap.put("isPublic", true);
        codeMap.put("uID", "null");
        DocumentReference newCode = db.collection("QRCodes").document();
        newCode.set(codeMap);
        return newCode.getId();
    }


}
