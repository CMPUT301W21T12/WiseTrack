package com.faanggang.wisetrack.controllers;

import android.util.Log;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class QRCodeManager {
    private FirebaseFirestore db;
    private BarcodeUploadListener listener;

    public interface BarcodeUploadListener {
        void onBCUploadSuccess();
        void onBCUploadFail();
    }
    public QRCodeManager( BarcodeUploadListener listener){
        db = FirebaseFirestore.getInstance();
        this.listener = listener;

    }


    public String GenerateQRString(String expID, int trialResult){
        Map<String, Object> codeMap = new HashMap<>();
        codeMap.put("expID", expID);
        codeMap.put("trialResult", trialResult);
        codeMap.put("isPublic", true);
        codeMap.put("uID", "null");
        DocumentReference newCode = db.collection("QRCodes").document();
        newCode.set(codeMap);
        return newCode.getId();
    }

    public void AddBarCode(String barcode, String expID, int trialResult, String userID){

        db.collection("QRCodes").document(barcode).get()
            .addOnCompleteListener(task ->{
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        listener.onBCUploadFail();
                    } else {
                        Map<String, Object> codeMap = new HashMap<>();
                        codeMap.put("expID", expID);
                        codeMap.put("trialResult", trialResult);
                        codeMap.put("isPublic", false);
                        codeMap.put("uID", userID);
                        db.collection("QRCodes").document(barcode).set(codeMap);
                        listener.onBCUploadSuccess();
                    }

                } else{
                    Log.d("QR", "Failed wtih: ", task.getException());
                }
            });
    }
}
