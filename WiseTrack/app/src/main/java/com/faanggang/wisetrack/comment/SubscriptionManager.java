package com.faanggang.wisetrack.comment;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class SubscriptionManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    public void addToSubscription(String expID, String userID){
        db.collection("Users").document(userID).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        ArrayList<String> subs = (ArrayList<String>) task.getResult().get("Subscriptions");
                        HashMap<String, Object> map = new HashMap<String,Object>();
                        subs.add(expID);
                        map.put("Subscriptions",subs);
                        db.collection("Users").document(userID).update(map);
                        Log.w("SUBSCRIPTION","added new twitch prime sub :)");
                    }else {
                        Log.w("SUBSCRIPTION","NO USER FOUND BRUH");
                    }
                });
    };
}