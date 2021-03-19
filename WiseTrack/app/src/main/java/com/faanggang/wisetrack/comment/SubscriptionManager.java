package com.faanggang.wisetrack.comment;
import android.util.Log;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class manages how user subscriptions are added and removed from users.
 *
 * */
public class SubscriptionManager {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    /**
    * This method updates the "Subscription" field of a user on the firebase.
    * @param expID
     * expID is the ID of the experiment that you are adding to.
    * @param userID
     * userID is the ID of the user that you want to edit the subscription list of.
    */
    public void addSubscription(String expID, String userID){
        db.collection("Users").document(userID).get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                ArrayList<String> subs = (ArrayList<String>) task.getResult().get("Subscriptions");
                if (!subs.contains(expID)){
                    HashMap<String, Object> map = new HashMap<String,Object>();
                    subs.add(expID);
                    map.put("subscriptions",subs);
                    db.collection("Users").document(userID).update(map);
                }
            }
        });
        db.collection("Experiments").document(expID).get()
        .addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                ArrayList<String> subs = (ArrayList<String>) task.getResult().get("Subscribers");
                if (!subs.contains(userID)){
                    HashMap<String, Object> map = new HashMap<String,Object>();
                    subs.add(userID);
                    map.put("subscribers",subs);
                    db.collection("Experiments").document(expID).update(map);
                }
            }
        });
    }

    /**
     * This method updates the "Subscription" field of a user on the firebase.
     * @param expID
     * expID is the ID of the experiment that you are removing.
     * @param userID
     * userID is the ID of the user that you want to edit the subscription list of.
     */
    public void removeSubscription(String expID, String userID){
        db.collection("Users").document(userID).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        ArrayList<String> subs = (ArrayList<String>) task.getResult().get("Subscriptions");
                        HashMap<String, Object> map = new HashMap<String,Object>();
                        if (subs.contains(expID)){
                            subs.remove(expID);
                        } else {Log.w("SUBSCRIPTION", "Trying to remove sub that doesn't exist");}
                        map.put("Subscriptions",subs);
                        db.collection("Users").document(userID).update(map);
                        Log.w("SUBSCRIPTION","added new twitch prime sub :)");
                    }else {
                        Log.w("SUBSCRIPTION","NO USER FOUND BRUH");
                    }
                });
    }
}
