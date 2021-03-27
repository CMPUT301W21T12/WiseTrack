package com.faanggang.wisetrack.controllers;

import android.graphics.Bitmap;

import com.faanggang.wisetrack.model.executeTrial.Trial;
import com.faanggang.wisetrack.model.experiment.Experiment;
import com.google.firebase.firestore.FirebaseFirestore;

public class QRManager {
    FirebaseFirestore db;

    public QRManager() {
        this.db = FirebaseFirestore.getInstance();
    }

    //public Bitmap drawQRCode() {
//
    //}
//
    //public String uploadQRCode(Experiment experiment, Trial trial){
//
    //}

}
